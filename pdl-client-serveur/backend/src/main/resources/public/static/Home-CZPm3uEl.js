import{d,r as i,c as o,a as n,w as c,v as p,F as v,b as g,e as f,o as l,t as h}from"./index-CAbDvhew.js";import{a as _}from"./http-api-BK82HC7n.js";const I=["value"],B=d({__name:"Home",setup(L){const s=i(-1),r=i([]);u();function u(){_.getImageList().then(a=>{r.value=a}).catch(a=>{console.log(a.message)})}function m(){f.push({name:"image",params:{id:s.value}})}return(a,t)=>(l(),o("div",null,[t[1]||(t[1]=n("h3",null,"Choose an image",-1)),n("div",null,[c(n("select",{"onUpdate:modelValue":t[0]||(t[0]=e=>s.value=e),onChange:m},[(l(!0),o(v,null,g(r.value,e=>(l(),o("option",{value:e.id,key:e.id},h(e.name),9,I))),128))],544),[[p,s.value]])])]))}});export{B as default};
