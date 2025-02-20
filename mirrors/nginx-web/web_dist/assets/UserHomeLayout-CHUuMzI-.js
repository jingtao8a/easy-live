import{b as g,ag as v,o as c,c as f,P as l,H as r,a as s,O as M,a4 as O,u as p,K as R,i as q,z as F,G as L,V as G,L as T,M as y,n as P,l as J,D as z,C as W,a5 as Q}from"./@vue-JlwsckWB.js";import{a as S,u as j}from"./vue-router-hI8gbx6m.js";import{_ as B,a as E,b as X}from"./index-D9sq5B9z.js";import{_ as Z}from"./Account-BeQtjicT.js";import{L as ee}from"./LayoutHeader-Bc0V7Q4q.js";import"./pinia-91x-qzso.js";import"./vue-demi-Dq6ymT-8.js";import"./element-plus-BCXmXNOW.js";import"./lodash-es-DpRhkxuT.js";import"./@vueuse-DS8A98-V.js";import"./@element-plus-Dbr4-WMN.js";import"./@popperjs-D9SI2xQl.js";import"./@ctrl-r5W6hzzQ.js";import"./dayjs-Cvt7qOjS.js";import"./artplayer-DYEsUqgO.js";import"./async-validator-DKvM95Vc.js";import"./memoize-one-BdPwpGay.js";import"./normalize-wheel-es-B6fDCfyv.js";import"./@floating-ui-DVIT0l11.js";import"./axios-upsvKRUO.js";import"./vue-cookies-DmNBPvI0.js";import"./@fingerprintjs-BlNouByb.js";import"./tslib-BLN2B0TR.js";import"./vue-cropper-BAfMpYow.js";import"./mitt-DJ65BbbF.js";import"./moment-C5S46NFB.js";import"./js-md5-Ddb6WJu6.js";const oe={class:"theme-body"},te={class:"theme-gird-list"},se=["onClick"],ae=["src"],le={key:0,class:"checked iconfont icon-checked"},ne=10,ue={__name:"UserHomeTheme",emits:["changeTheme"],setup(A,{expose:m,emit:V}){const{proxy:I}=q();S();const n=g(!1),o=g(0),C=V,w=async _=>{o.value=_,await I.Request({url:I.Api.saveTheme,params:{theme:_}})&&(n.value=!1,C("changeTheme",_))};return m({show:_=>{n.value=!0,o.value=_}}),(_,k)=>{const h=v("el-scrollbar"),t=v("el-drawer");return c(),f("div",oe,[l(t,{"lock-scroll":"",modelValue:n.value,"onUpdate:modelValue":k[0]||(k[0]=i=>n.value=i),title:"选择背景",direction:"btt",size:"45%"},{default:r(()=>[l(h,{height:"100%"},{default:r(()=>[s("div",te,[(c(),f(M,null,O(ne,i=>s("div",{class:"grid-item",onClick:b=>w(i)},[s("img",{src:p(I).Utils.getLocalImage("uhome-bg/"+i+".png")},null,8,ae),o.value==i?(c(),f("div",le)):R("",!0)],8,se)),64))])]),_:1})]),_:1},8,["modelValue"])])}}},re=B(ue,[["__scopeId","data-v-06b104d1"]]),ie={__name:"UserInfoEdit",emits:["reload"],setup(A,{expose:m,emit:V}){const{proxy:I}=q();j(),S(),E();const n=g({show:!1,title:"修改用户信息",buttons:[{type:"primary",text:"确定",click:h=>{k()}}]});F("cutImageCallback",({coverImage:h})=>{o.value.avatar=h});const o=g({}),C=g(),w={avatar:[{required:!0,message:"请上传头像"}],nickName:[{required:!0,message:"请输入昵称"}],sex:[{required:!0,message:"请选择性别"}]};m({show:h=>{n.value.show=!0,P(()=>{C.value.resetFields(),o.value=Object.assign({},h)})}});const _=V,k=()=>{C.value.validate(async h=>{if(!h)return;let t={};if(Object.assign(t,o.value),t.avatar instanceof File){const b=await X(t.avatar);if(!b)return;t.avatar=b}await I.Request({url:I.Api.uHomeUpdateUserInfo,params:t})&&(n.value.show=!1,I.Message.success("修改成功"),_("reload"))})};return(h,t)=>{const i=v("el-form-item"),b=v("ImageCoverSelect"),U=v("el-input"),N=v("el-radio"),D=v("el-radio-group"),H=v("el-date-picker"),d=v("el-form"),e=v("Dialog");return c(),L(e,{show:n.value.show,title:n.value.title,buttons:n.value.buttons,width:"500px",onClose:t[7]||(t[7]=u=>n.value.show=!1)},{default:r(()=>[l(d,{model:o.value,rules:w,ref_key:"formDataRef",ref:C,"label-width":"80px",onSubmit:t[6]||(t[6]=G(()=>{},["prevent"]))},{default:r(()=>[l(i,{label:"UID",prop:""},{default:r(()=>[T(y(o.value.userId),1)]),_:1}),l(i,{label:"头像",prop:"avatar"},{default:r(()=>[l(b,{coverWidth:150,cutWidth:150,scale:1,coverImage:o.value.avatar},null,8,["coverImage"])]),_:1}),l(i,{label:"昵称",prop:"nickName"},{default:r(()=>[l(U,{clearable:"",placeholder:"昵称",modelValue:o.value.nickName,"onUpdate:modelValue":t[0]||(t[0]=u=>o.value.nickName=u),maxlength:20,"show-word-limit":""},null,8,["modelValue"])]),_:1}),l(i,{label:"性别",prop:"sex"},{default:r(()=>[l(D,{modelValue:o.value.sex,"onUpdate:modelValue":t[1]||(t[1]=u=>o.value.sex=u)},{default:r(()=>[l(N,{value:0},{default:r(()=>t[8]||(t[8]=[T("女")])),_:1}),l(N,{value:1},{default:r(()=>t[9]||(t[9]=[T("男")])),_:1}),l(N,{value:2},{default:r(()=>t[10]||(t[10]=[T("保密")])),_:1})]),_:1},8,["modelValue"])]),_:1}),l(i,{label:"出生日期",prop:"birthday"},{default:r(()=>[l(H,{modelValue:o.value.birthday,"onUpdate:modelValue":t[2]||(t[2]=u=>o.value.birthday=u),type:"date",placeholder:"请选择出生日期","value-format":"YYYY-MM-DD"},null,8,["modelValue"])]),_:1}),l(i,{label:"学校",prop:"school"},{default:r(()=>[l(U,{clearable:"",placeholder:"学校信息",modelValue:o.value.school,"onUpdate:modelValue":t[3]||(t[3]=u=>o.value.school=u),maxlength:150,"show-word-limit":""},null,8,["modelValue"])]),_:1}),l(i,{label:"简介",prop:"personIntroduction"},{default:r(()=>[l(U,{clearable:"",placeholder:"简介",type:"textarea",rows:3,maxlength:80,resize:"none","show-word-limit":"",modelValue:o.value.personIntroduction,"onUpdate:modelValue":t[4]||(t[4]=u=>o.value.personIntroduction=u)},null,8,["modelValue"])]),_:1}),l(i,{label:"公告",prop:"noticeInfo"},{default:r(()=>[l(U,{clearable:"",placeholder:"公告",type:"textarea",rows:5,maxlength:300,resize:"none","show-word-limit":"",modelValue:o.value.noticeInfo,"onUpdate:modelValue":t[5]||(t[5]=u=>o.value.noticeInfo=u)},null,8,["modelValue"])]),_:1})]),_:1},8,["model"])]),_:1},8,["show","title","buttons"])}}},de={class:"user-home-body"},ce={class:"user-info-panel"},me={class:"avatar"},ve={class:"user-info"},pe={class:"user-name"},fe={class:"introduction"},_e={key:0,class:"focus-panel"},he={class:"home-nav"},ye={class:"nav-panel"},ge={class:"search"},Ie={class:"count-info"},be={class:"count-value"},we={key:1,class:"count-item"},ke={class:"count-value"},xe={class:"count-value"},Ve={key:3,class:"count-item"},Ce={class:"count-value"},Ue={class:"count-item"},Ne={class:"count-value"},$e={class:"count-item"},Re={class:"count-value"},Te={class:"user-home-content"},Fe={__name:"UserHomeLayout",setup(A){const{proxy:m}=q(),V=j(),I=S(),n=E(),o=V.params.userId,C=g([{name:"主页",path:"/user/"+o,icon:"icon-home",pathNames:["uhome","uhomeFans","uhomeFocus"]},{name:"投稿",path:"/user/"+o+"/video",icon:"icon-play",pathNames:["uhomeMyVideo"]},{name:"视频列表",path:"/user/"+o+"/series",icon:"icon-playlist",pathNames:["uhomeSeries","uhomeSeriesDetail"]},{name:"收藏",path:"/user/"+o+"/collection",icon:"icon-collection",pathNames:["collection"]}]),w=J(()=>n.userInfo.userId==o),a=g({});F("userInfo",a);const _=async()=>{let d=await m.Request({url:m.Api.uHomeGetUsesrInfo,params:{userId:o}});if(!d)return;a.value=d.data;let e=d.data.noticeInfo;e&&(e=e.replace(/\r\n/g,"<br>"),e=e.replace(/\n/g,"<br>"),a.value.noticeInfo=e),Object.keys(n.userInfo).length>0&&n.userInfo.userId==o&&n.userInfo.avatar!==d.data.avatar&&(n.userInfo.avatar=d.data.avatar)};_();const k=g(),h=()=>{k.value.show(a.value)},t=async(d,e=0,u)=>{if(Object.keys(n.userInfo).length==0){n.setLogin(!0);return}await m.Request({url:m.Api.uHomeFocus,showLoading:!0,params:{focusUserId:d}})&&(e==0?(a.value.haveFocus=!0,a.value.fansCount++):a.value.focusCount++,u&&u())},i=async(d,e=0,u)=>{await m.Request({url:m.Api.uHomeCancelFocus,showLoading:!0,params:{focusUserId:d}})&&(e==0?(a.value.haveFocus=!1,a.value.fansCount--):a.value.focusCount--,u&&u())};F("cancelFocusUser",(d,e)=>{i(d,1,e)}),F("focusUser",(d,e)=>{t(d,1,e)});const b=g(),U=()=>{I.push({path:`/user/${V.params.userId}/video`,query:{videoName:b.value}})},N=g(),D=()=>{N.value.show(a.value.theme)},H=d=>{a.value.theme=d};return(d,e)=>{const u=v("Avatar"),$=v("router-link"),Y=v("el-input"),K=v("router-view");return c(),f(M,null,[s("div",{class:"header-fixed",style:z({"max-width":p(m).bodyMaxWidth+"px","min-width":p(m).bodyMinWidth+"px"})},[l(ee,{theme:"dark"})],4),s("div",{class:"user-home-body-container",style:z({"max-width":p(m).bodyMaxWidth+"px","min-width":p(m).bodyMinWidth+"px","background-image":`url(${p(m).Utils.getLocalImage("uhome-bg/"+(a.value.theme||0)+".png")})`})},[s("div",de,[s("div",ce,[s("div",me,[l(u,{width:90,avatar:a.value.avatar,userId:a.value.userId},null,8,["avatar","userId"])]),s("div",ve,[s("div",pe,[s("div",null,y(a.value.nickName),1),a.value.sex==0||a.value.sex==1?(c(),f("div",{key:0,class:W(["iconfont",a.value.sex==0?"icon-sexw":"icon-sexm"])},null,2)):R("",!0),w.value?(c(),f("div",{key:1,class:"iconfont icon-edit",onClick:h})):R("",!0)]),s("div",fe,y(a.value.personIntroduction),1)]),w.value?R("",!0):(c(),f("div",_e,[a.value.haveFocus?(c(),f("div",{key:0,class:"btn-focus btn-cancel-focus",onClick:e[0]||(e[0]=x=>i(p(o)))}," 取消关注 ")):(c(),f("div",{key:1,class:"btn-focus",onClick:e[1]||(e[1]=x=>t(p(o)))}," 关注 "))]))]),s("div",he,[s("div",ye,[(c(!0),f(M,null,O(C.value,x=>(c(),L($,{class:W(["nav-item iconfont",x.icon,x.pathNames.includes(p(V).name)?"active":""]),to:x.path},{default:r(()=>[T(y(x.name),1)]),_:2},1032,["class","to"]))),256))]),s("div",ge,[l(Y,{placeholder:"搜视频",style:{width:"200px"},modelValue:b.value,"onUpdate:modelValue":e[2]||(e[2]=x=>b.value=x),onKeyup:Q(U,["enter"])},{suffix:r(()=>e[3]||(e[3]=[s("span",{class:"iconfont icon-search"},null,-1)])),_:1},8,["modelValue"])]),s("div",Ie,[w.value?(c(),L($,{key:0,class:"count-item",to:`/user/${p(o)}/focus`},{default:r(()=>[e[4]||(e[4]=s("div",{class:"title-info"},"关注数",-1)),s("div",be,y(a.value.focusCount),1)]),_:1},8,["to"])):(c(),f("div",we,[e[5]||(e[5]=s("div",{class:"title-info"},"关注数",-1)),s("div",ke,y(a.value.focusCount),1)])),w.value?(c(),L($,{key:2,class:"count-item",to:`/user/${p(o)}/fans`},{default:r(()=>[e[6]||(e[6]=s("div",{class:"title-info"},"粉丝数",-1)),s("div",xe,y(a.value.fansCount),1)]),_:1},8,["to"])):(c(),f("div",Ve,[e[7]||(e[7]=s("div",{class:"title-info"},"粉丝数",-1)),s("div",Ce,y(a.value.fansCount),1)])),s("div",Ue,[e[8]||(e[8]=s("div",{class:"title-info"},"获赞数",-1)),s("div",Ne,y(a.value.likeCount),1)]),s("div",$e,[e[9]||(e[9]=s("div",{class:"title-info"},"播放数",-1)),s("div",Re,y(a.value.playCount),1)])])]),s("div",Te,[l(K)])]),p(n).userInfo&&a.value.userId==p(n).userInfo.userId?(c(),f("div",{key:0,class:"change-them-btn",onClick:D})):R("",!0)],4),l(Z),l(ie,{ref_key:"userInfoEditRef",ref:k,onReload:_},null,512),l(re,{ref_key:"userHomeThemeRef",ref:N,onChangeTheme:H},null,512)],64)}}},no=B(Fe,[["__scopeId","data-v-51d92fd9"]]);export{no as default};
