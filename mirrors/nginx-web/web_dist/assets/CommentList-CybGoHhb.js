import{_ as V,V as D}from"./Table-DKIv2YqD.js";import{u as L,a as R}from"./vue-router-hI8gbx6m.js";import{_ as H}from"./index-D9sq5B9z.js";import{b as m,c as _,P as t,H as n,o as v,a as o,L as d,M as r,O as $,K as C,u as A,i as q,ag as f}from"./@vue-JlwsckWB.js";import"./pinia-91x-qzso.js";import"./vue-demi-Dq6ymT-8.js";import"./element-plus-BCXmXNOW.js";import"./lodash-es-DpRhkxuT.js";import"./@vueuse-DS8A98-V.js";import"./@element-plus-Dbr4-WMN.js";import"./@popperjs-D9SI2xQl.js";import"./@ctrl-r5W6hzzQ.js";import"./dayjs-Cvt7qOjS.js";import"./artplayer-DYEsUqgO.js";import"./async-validator-DKvM95Vc.js";import"./memoize-one-BdPwpGay.js";import"./normalize-wheel-es-B6fDCfyv.js";import"./@floating-ui-DVIT0l11.js";import"./axios-upsvKRUO.js";import"./vue-cookies-DmNBPvI0.js";import"./@fingerprintjs-BlNouByb.js";import"./tslib-BLN2B0TR.js";import"./vue-cropper-BAfMpYow.js";import"./mitt-DJ65BbbF.js";import"./moment-C5S46NFB.js";const B={class:"comment-panel"},O={class:"comment-info"},P={class:"comment"},T={class:"content"},z={key:0,class:"image-show"},M={class:"time-info"},U={class:"time"},j=["onClick"],E={class:"video-name"},F={__name:"CommentList",setup(K){const{proxy:s}=q();R();const I=L(),g=m(I.query.videoId),x=i=>{g.value=i,u()},b=[{label:"评论信息",scopedSlots:"slotComment"},{label:"视频信息",scopedSlots:"slotVideo",width:150}],y=m(),k=m({extHeight:10}),l=m({}),u=async i=>{let a={pageNo:l.value.pageNo,pageSize:l.value.pageSize,videoId:g.value},c=await s.Request({url:s.Api.ucLoadComment,params:a});c&&Object.assign(l.value,c.data)},S=i=>{s.Confirm({message:"确定要删除吗？",okfun:async()=>{await s.Request({url:s.Api.ucDelComment,params:{commentId:i}})&&(s.Message.success("删除成功"),u())}})};return(i,a)=>{const c=f("Avatar"),p=f("router-link"),h=f("Cover");return v(),_("div",B,[t(D,{onLoadData:x}),t(V,{ref_key:"tableInfoRef",ref:y,columns:b,fetch:u,dataSource:l.value,options:k.value,extHeight:k.value.extHeight},{slotComment:n(({index:N,row:e})=>[o("div",O,[t(c,{avatar:e.avatar,userId:e.userId},null,8,["avatar","userId"]),o("div",P,[o("div",null,[t(p,{target:"_blank",class:"a-link nick-name",to:`/user/${e.userId}`},{default:n(()=>[d(r(e.nickName),1)]),_:2},1032,["to"]),e.replyUserId?(v(),_($,{key:0},[a[0]||(a[0]=d(" 回复@ ")),t(p,{target:"_blank",class:"a-link nick-name",to:`/user/${e.replyUserId}`},{default:n(()=>[d(r(e.replyNickName),1)]),_:2},1032,["to"]),a[1]||(a[1]=d("的评论 "))],64)):C("",!0)]),o("div",T,r(e.content),1),e.imgPath?(v(),_("div",z,[t(h,{source:e.imgPath+A(s).imageThumbnailSuffix,preview:!0,fit:"cover"},null,8,["source"])])):C("",!0),o("div",M,[o("div",U,r(e.postTime),1),o("div",{class:"iconfont icon-delete",onClick:G=>S(e.commentId)},null,8,j)])])])]),slotVideo:n(({index:N,row:e})=>[t(p,{to:`/video/${e.videoId}`,target:"_blank",class:"a-link"},{default:n(()=>[t(h,{source:e.videoCover},null,8,["source"]),o("div",E,r(e.videoName),1)]),_:2},1032,["to"])]),_:1},8,["dataSource","options","extHeight"])])}}},ke=H(F,[["__scopeId","data-v-084fea22"]]);export{ke as default};
