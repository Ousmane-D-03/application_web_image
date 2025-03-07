package pdl.backend;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ImageRepository imageRepository;

    @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable("id") long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image.get().getData());
        }
        return new ResponseEntity<>("Image id=" + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/images/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImage(@PathVariable("id") long id) {
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            imageRepository.delete(image.get());
            return new ResponseEntity<>("Image id=" + id + " deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Image id=" + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file) {
        String contentType = file.getContentType();
        if (!contentType.equals(MediaType.IMAGE_JPEG.toString())) {
            return new ResponseEntity<>("Only JPEG file format supported", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        try {
            Image image = new Image(file.getOriginalFilename(), file.getBytes());
            imageRepository.save(image);
        } catch (IOException e) {
            return new ResponseEntity<>("Failure to read file", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Image uploaded", HttpStatus.OK);
    }

    @RequestMapping(value = "/images", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ArrayNode getImageList() {
        List<Image> images = imageRepository.findAll();
        ArrayNode nodes = mapper.createArrayNode();
        for (Image image : images) {
            ObjectNode objectNode = mapper.createObjectNode();
            objectNode.put("id", image.getId());
            objectNode.put("name", image.getName());
            nodes.add(objectNode);
        }
        return nodes;
    }
}