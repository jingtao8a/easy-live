import{b as m,ag as i,o as n,G as p,H as l,P as u,V as F,c as k,O as x,K as _,a4 as z,a as d,M as y,u as G,L as b,i as K,n as P}from"./@vue-JlwsckWB.js";import{u as T,a as $}from"./vue-router-hI8gbx6m.js";import{_ as J}from"./index-D9sq5B9z.js";const Q={class:"video-item"},W={class:"check"},X={class:"cover"},Y={class:"video-info"},Z={class:"video-name"},ee={class:"play-info"},oe={class:"iconfont icon-play2"},se={class:"create-time"},te={class:"op-btns"},ae={__name:"VideoSeriesEdit",emits:["reload"],setup(le,{expose:C,emit:S}){const{proxy:r}=K(),I=T();$();const v=m({show:!1,title:"视频列表"}),c=m(0),s=m(1),a=m({videoIds:[]}),f=m(),D={seriesName:[{required:!0,message:"请输入名称"}]};C({show:(t={},e=0)=>{H(),c.value=e,e===0||e==1?s.value=1:e==2&&(s.value=2),v.value.show=!0,P(()=>{f.value.resetFields(),a.value=Object.assign({},t),a.value.videoIds=[]})}});const N=S,R=()=>{v.value.show=!1},L=()=>{f.value.validate(async t=>{t&&(s.value=2)})},A=()=>{f.value.validate(async t=>{t&&(s.value=1)})},B=()=>{f.value.validate(async t=>{if(!t)return;const e=Object.assign({},a.value);if(e.videoIds.length==0&&s.value==2){r.Message.warning("请选择视频");return}e.videoIds=e.videoIds.join(","),await r.Request({url:c.value==2?r.Api.uHomeSeriesSaveSeriesVideo:r.Api.uHomeSeriesSaveVideoSeries,params:e})&&(r.Message.success("保存成功"),v.value.show=!1,N("reload"))})},V=m([]),H=async()=>{let t=await r.Request({url:r.Api.uHomeSeriesLoadAllVideo,params:{seriesId:I.params.seriesId}});t&&(V.value=t.data)};return(t,e)=>{const h=i("el-input"),g=i("el-form-item"),M=i("el-checkbox"),U=i("Cover"),j=i("el-checkbox-group"),q=i("el-scrollbar"),w=i("el-button"),E=i("el-form"),O=i("Dialog");return n(),p(O,{show:v.value.show,title:v.value.title,buttons:v.value.buttons,width:"500px",showCancel:!1,onClose:e[4]||(e[4]=o=>v.value.show=!1)},{default:l(()=>[u(E,{model:a.value,rules:D,ref_key:"formDataRef",ref:f,"label-width":"80px",onSubmit:e[3]||(e[3]=F(()=>{},["prevent"]))},{default:l(()=>[s.value==1?(n(),k(x,{key:0},[u(g,{label:"列表名称",prop:"seriesName"},{default:l(()=>[u(h,{clearable:"",placeholder:"请填写名称",modelValue:a.value.seriesName,"onUpdate:modelValue":e[0]||(e[0]=o=>a.value.seriesName=o),maxlength:100,"show-word-limit":""},null,8,["modelValue"])]),_:1}),u(g,{label:"列表简介",prop:""},{default:l(()=>[u(h,{clearable:"",placeholder:"请填写简介（选填）",type:"textarea",rows:5,maxlength:200,"show-word-limit":"",resize:"none",modelValue:a.value.seriesDescription,"onUpdate:modelValue":e[1]||(e[1]=o=>a.value.seriesDescription=o)},null,8,["modelValue"])]),_:1})],64)):_("",!0),s.value==2?(n(),p(q,{key:1,"max-height":"400px"},{default:l(()=>[u(j,{modelValue:a.value.videoIds,"onUpdate:modelValue":e[2]||(e[2]=o=>a.value.videoIds=o)},{default:l(()=>[(n(!0),k(x,null,z(V.value,o=>(n(),k("div",Q,[d("div",W,[u(M,{value:o.videoId},null,8,["value"])]),d("div",X,[u(U,{source:o.videoCover},null,8,["source"])]),d("div",Y,[d("div",Z,y(o.videoName),1),d("div",ee,[d("div",oe,y(o.playCount),1),d("div",se,y(G(r).Utils.formatDate(o.createTime)),1)])])]))),256))]),_:1},8,["modelValue"])]),_:1})):_("",!0),d("div",te,[s.value==1||c.value==2?(n(),p(w,{key:0,link:"",onClick:R},{default:l(()=>e[5]||(e[5]=[b(" 取消 ")])),_:1})):_("",!0),s.value==1&&c.value!=1?(n(),p(w,{key:1,type:"primary",onClick:L},{default:l(()=>e[6]||(e[6]=[b(" 下一步 ")])),_:1})):_("",!0),s.value==2&&c.value!=2?(n(),p(w,{key:2,type:"primary",onClick:A},{default:l(()=>e[7]||(e[7]=[b(" 上一步 ")])),_:1})):_("",!0),s.value==2||c.value==1?(n(),p(w,{key:3,type:"primary",onClick:B},{default:l(()=>e[8]||(e[8]=[b(" 确定 ")])),_:1})):_("",!0)])]),_:1},8,["model"])]),_:1},8,["show","title","buttons"])}}},de=J(ae,[["__scopeId","data-v-66962941"]]);export{de as V};
