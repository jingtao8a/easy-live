import{V as N}from"./VideoItem-DSA33764.js";import{u as y,a as g}from"./vue-router-hI8gbx6m.js";import{_ as L}from"./index-D9sq5B9z.js";import{b as s,e as k,c,a as v,P as m,G as x,H as D,ag as p,o as d,i as I}from"./@vue-JlwsckWB.js";/* empty css                                                                  */import"./pinia-91x-qzso.js";import"./vue-demi-Dq6ymT-8.js";import"./element-plus-BCXmXNOW.js";import"./lodash-es-DpRhkxuT.js";import"./@vueuse-DS8A98-V.js";import"./@element-plus-Dbr4-WMN.js";import"./@popperjs-D9SI2xQl.js";import"./@ctrl-r5W6hzzQ.js";import"./dayjs-Cvt7qOjS.js";import"./artplayer-DYEsUqgO.js";import"./async-validator-DKvM95Vc.js";import"./memoize-one-BdPwpGay.js";import"./normalize-wheel-es-B6fDCfyv.js";import"./@floating-ui-DVIT0l11.js";import"./axios-upsvKRUO.js";import"./vue-cookies-DmNBPvI0.js";import"./@fingerprintjs-BlNouByb.js";import"./tslib-BLN2B0TR.js";import"./vue-cropper-BAfMpYow.js";import"./mitt-DJ65BbbF.js";import"./moment-C5S46NFB.js";const b={class:"video-panel"},B={class:"video-title-panel"},C={key:0},T={__name:"VideoList",setup(h){const{proxy:l}=I(),n=y();g();const r=s(0),u=s(),t=s({}),a=async()=>{let o={pageNo:t.value.pageNo,videoName:u.value,orderType:r.value};o.userId=n.params.userId;let e=await l.Request({url:l.Api.uHomeLoadVideo,params:o});e&&(t.value=e.data)};return a(),k(()=>n.query.videoName,(o,e)=>{u.value=o,a()},{immediate:!0,deep:!0}),(o,e)=>{const _=p("MyTab"),V=p("NoData"),f=p("DataGridList");return d(),c("div",b,[v("div",B,[e[1]||(e[1]=v("div",{class:"video-title"},"我的视频",-1)),m(_,{modelValue:r.value,"onUpdate:modelValue":e[0]||(e[0]=i=>r.value=i),onClickHandler:a,tags:[{name:"最新发布"},{name:"最多播放"},{name:"最多收藏"}]},null,8,["modelValue"])]),t.value.list&&t.value.list.length==0?(d(),c("div",C,[m(V,{msg:"空间主人还没有投过视频哦~~"})])):(d(),x(f,{key:1,dataSource:t.value,onLoadData:a},{default:D(({data:i})=>[m(N,{data:i},null,8,["data"])]),_:1},8,["dataSource"]))])}}},ae=L(T,[["__scopeId","data-v-38779a7d"]]);export{ae as default};
