import{b as u,ag as a,o as k,G as U,H as s,P as t,V as j,L,K as T,i as q,n as B,a as e,c as I,O as D,a4 as te,C as F,M as n,u as K,a5 as oe,D as se}from"./@vue-o39Kj8Hc.js";import{b as E,u as G}from"./vue-router-B5Ds_jdF.js";import{_ as J,m as ae}from"./index-Di0cqdVb.js";import"./pinia-DRS-luHF.js";import"./vue-demi-Dq6ymT-8.js";import"./element-plus-DqxZfY1O.js";import"./lodash-es-DpRhkxuT.js";import"./@vueuse-2MWXcMyd.js";import"./@element-plus-B683oq9Q.js";import"./@popperjs-D9SI2xQl.js";import"./@ctrl-r5W6hzzQ.js";import"./dayjs-Cvt7qOjS.js";import"./artplayer-DYEsUqgO.js";import"./async-validator-DKvM95Vc.js";import"./memoize-one-BdPwpGay.js";import"./normalize-wheel-es-B6fDCfyv.js";import"./@floating-ui-DVIT0l11.js";import"./vue-cookies-DmNBPvI0.js";import"./vue-cropper-B5nAOkqF.js";import"./mitt-DJ65BbbF.js";import"./hls.js-DnSDjNdB.js";import"./artplayer-plugin-danmuku-CU64Le-w.js";import"./axios-upsvKRUO.js";import"./moment-C5S46NFB.js";const le={__name:"VideoAudit",emits:["reload"],setup(H,{expose:c,emit:m}){const{proxy:v}=q();E(),G();const _=u({}),p=u(),$={status:[{required:!0,message:"请选择审核结果"}],reason:[{required:!0,message:"请输入拒绝理由"}]},g=u({show:!1,title:"审核",buttons:[{type:"primary",text:"确定",click:C=>{A()}}]});c({show:C=>{g.value.show=!0,B(()=>{p.value.resetFields(),_.value={videoId:C}})}});const x=m,A=async()=>{p.value.validate(async C=>{if(!C)return;let d={};Object.assign(d,_.value),await v.Request({url:v.Api.auditVideo,params:d})&&(g.value.show=!1,v.Message.success("审核成功"),x("reload"))})};return(C,d)=>{const V=a("el-radio"),h=a("el-radio-group"),l=a("el-form-item"),R=a("el-input"),o=a("el-form"),r=a("Dialog");return k(),U(r,{show:g.value.show,title:g.value.title,buttons:g.value.buttons,width:"600px",showCancel:!0,onClose:d[3]||(d[3]=w=>g.value.show=!1)},{default:s(()=>[t(o,{model:_.value,rules:$,ref_key:"formDataRef",ref:p,"label-width":"80px",onSubmit:d[2]||(d[2]=j(()=>{},["prevent"]))},{default:s(()=>[t(l,{label:"审核结果",prop:"status"},{default:s(()=>[t(h,{modelValue:_.value.status,"onUpdate:modelValue":d[0]||(d[0]=w=>_.value.status=w)},{default:s(()=>[t(V,{value:3},{default:s(()=>d[4]||(d[4]=[L("审核通过")])),_:1}),t(V,{value:4},{default:s(()=>d[5]||(d[5]=[L("审核不通过")])),_:1})]),_:1},8,["modelValue"])]),_:1}),_.value.status==4?(k(),U(l,{key:0,label:"拒绝理由",prop:"reason"},{default:s(()=>[t(R,{resize:"none",type:"textarea",rows:4,clearable:"",placeholder:"请输入拒绝理由",modelValue:_.value.reason,"onUpdate:modelValue":d[1]||(d[1]=w=>_.value.reason=w),"show-word-limit":"",maxlength:200},null,8,["modelValue"])]),_:1})):T("",!0)]),_:1},8,["model"])]),_:1},8,["show","title","buttons"])}}},ie={class:"video-detail"},ne={class:"video-info"},de=["onClick"],re={key:0,class:"playing"},ce=["title"],ue={class:"duration"},pe={class:"video-base-info"},me={class:"base-info-item"},ve={class:"item-value"},_e={class:"base-info-item"},fe={class:"item-value"},ye={class:"base-info-item"},ge={class:"item-value"},he={key:0,class:"base-info-item"},be={class:"item-value"},Ce={class:"base-info-item"},ke={class:"item-value"},we={class:"base-info-item"},Ve={class:"item-value"},$e={class:"video-play"},Ie={__name:"VideoDetail",setup(H,{expose:c}){const{proxy:m}=q();E(),G();const v=u({show:!1}),_=u("video"),p=u(),$=u([]),g=u(),y=u(1),x=h=>{v.value.show=!0,p.value=Object.assign({},h),y.value=1,A()},A=async()=>{let h=await m.Request({url:m.Api.loadVideoPList,params:{videoId:p.value.videoId}});h&&($.value=h.data,B(()=>{g.value.showPlayer(window.innerHeight-150),d()}))},C=h=>{y.value=h,d()},d=()=>{ae.emit("changeP",$.value[y.value-1].fileId)},V=()=>{v.value.show=!1,g.value.destroyPlayer()};return c({show:x}),(h,l)=>{const R=a("el-scrollbar"),o=a("el-tab-pane"),r=a("el-tabs"),w=a("Player"),S=a("Dialog");return k(),U(S,{show:v.value.show,title:v.value.title,buttons:v.value.buttons,width:"90%",showCancel:!1,onClose:V},{default:s(()=>[e("div",ie,[e("div",ne,[t(r,{modelValue:_.value,"onUpdate:modelValue":l[0]||(l[0]=b=>_.value=b)},{default:s(()=>[t(o,{label:"视频分P",name:"video"},{default:s(()=>[l[1]||(l[1]=e("div",{class:"video-tips"},"红色标题代表视频有更新",-1)),t(R,{"max-height":400,class:"video-list"},{default:s(()=>[(k(!0),I(D,null,te($.value,(b,N)=>(k(),I("div",{class:F(["video-item",N==y.value-1?"active":""]),onClick:P=>C(N+1)},[N==y.value-1?(k(),I("div",re)):T("",!0),e("div",{class:F(["title",b.updateType==1?"update":""]),title:b.title}," P"+n(N+1)+" "+n(b.fileName),11,ce),e("div",ue,n(K(m).Utils.convertSecondsToHMS(b.duration)),1)],10,de))),256))]),_:1})]),_:1}),t(o,{label:"基本信息",name:"base"},{default:s(()=>[e("div",pe,[e("div",me,[l[2]||(l[2]=e("div",{class:"item-title"},"标题：",-1)),e("div",ve,n(p.value.videoName),1)]),e("div",_e,[l[3]||(l[3]=e("div",{class:"item-title"},"发布人：",-1)),e("div",fe,n(p.value.nickName),1)]),e("div",ye,[l[4]||(l[4]=e("div",{class:"item-title"},"类型：",-1)),e("div",ge,n(p.value.postType==0?"自制":"转载"),1)]),p.value.postType==1?(k(),I("div",he,[l[5]||(l[5]=e("div",{class:"item-title"},"资源说明：",-1)),e("div",be,n(p.value.originInfo),1)])):T("",!0),e("div",Ce,[l[6]||(l[6]=e("div",{class:"item-title"},"标签：",-1)),e("div",ke,n(p.value.tags),1)]),e("div",we,[l[7]||(l[7]=e("div",{class:"item-title"},"简介：",-1)),e("div",Ve,n(p.value.introduction),1)])])]),_:1})]),_:1},8,["modelValue"])]),e("div",$e,[t(w,{ref_key:"playerRef",ref:g,autoplay:!1},null,512)])])]),_:1},8,["show","title","buttons"])}}},xe=J(Ie,[["__scopeId","data-v-f32ed0d3"]]),Ne={class:"top-panel"},Re={class:"cover-info"},Se={class:"duration"},Te={class:"video-info"},Ae={class:"video-name"},De={class:"user-name iconfont icon-upzhu"},Le={class:"video-count"},Pe={class:"iconfont icon-play-solid"},ze={class:"iconfont icon-like-solid"},Oe={class:"iconfont icon-danmu-solid"},Ue={class:"iconfont icon-comment-solid"},je={class:"iconfont icon-toubi"},qe={class:"iconfont icon-collection-solid"},He={class:"row-op-panel"},Me=["onClick"],Fe=["onClick"],Be=["onClick"],Ke=["onClick"],Ee={__name:"VideoList",setup(H){const{proxy:c}=q();u(JSON.parse(sessionStorage.getItem("userInfo"))||{menuList:[]});const m=u({}),v=u({}),_=u({extHeight:0}),p={0:"#e6a23c",1:"#f56c6c",2:"#e6a23c",3:"#67c23a",4:"#f56c6c"},$=[{label:"封面",prop:"videoCover",width:220,scopedSlots:"videoCover"},{label:"视频信息",prop:"videoName",scopedSlots:"videoName"},{label:"最后更新时间",prop:"lastUpdateTime",width:200},{label:"状态",prop:"statusName",width:100,scopedSlots:"statusName"},{label:"推荐",prop:"recommendType",width:100,scopedSlots:"recommendType"},{label:"操作",prop:"operation",width:190,scopedSlots:"slotOperation"}],g=u(),y=async()=>{let o={pageNo:v.value.pageNo,pageSize:v.value.pageSize};Object.assign(o,m.value),o.categoryIdArray&&o.categoryIdArray.length==2?o.categoryId=o.categoryIdArray[1]:o.categoryIdArray&&o.categoryIdArray.length==1&&(o.pCategoryId=o.categoryIdArray[0]),delete o.categoryIdArray;let r=await c.Request({url:c.Api.loadVideo,params:o});r&&Object.assign(v.value,r.data)},x=u([]);(async()=>{let o=await c.Request({url:c.Api.loadCategory});o&&(x.value=o.data)})();const C=u(),d=o=>{C.value.show(o)},V=u(),h=o=>{V.value.show(o.videoId)},l=o=>{c.Confirm({message:`确定要删除【${o.videoName}】吗？`,okfun:async()=>{await c.Request({url:c.Api.deleteVideo,params:{videoId:o.videoId}})&&(c.Message.success("操作成功"),y())}})},R=o=>{const r=o.recommendType==0?"推荐":"取消推荐";c.Confirm({message:`确定要【${r}】【${o.videoName}】吗？`,okfun:async()=>{await c.Request({url:c.Api.recommendVideo,params:{videoId:o.videoId}})&&(c.Message.success("操作成功"),y())}})};return(o,r)=>{const w=a("el-input"),S=a("el-form-item"),b=a("el-col"),N=a("el-cascader"),P=a("el-option"),W=a("el-select"),Q=a("el-button"),X=a("el-row"),Y=a("el-form"),M=a("el-card"),Z=a("Cover"),z=a("el-divider"),ee=a("Table");return k(),I(D,null,[e("div",Ne,[t(M,null,{default:s(()=>[t(Y,{model:m.value,"label-width":"70px","label-position":"right"},{default:s(()=>[t(X,null,{default:s(()=>[t(b,{span:5},{default:s(()=>[t(S,{label:"视频名称"},{default:s(()=>[t(w,{class:"password-input",modelValue:m.value.videoNameFuzzy,"onUpdate:modelValue":r[0]||(r[0]=f=>m.value.videoNameFuzzy=f),clearable:"",placeholder:"支持模糊搜索",onKeyup:oe(y,["enter"])},null,8,["modelValue"])]),_:1})]),_:1}),t(b,{span:5},{default:s(()=>[t(S,{label:"分类"},{default:s(()=>[t(N,{style:{width:"100%"},modelValue:m.value.categoryIdArray,"onUpdate:modelValue":r[1]||(r[1]=f=>m.value.categoryIdArray=f),options:x.value,clearable:!0,props:{value:"categoryId",label:"categoryName",checkStrictly:!0}},null,8,["modelValue","options"])]),_:1})]),_:1}),t(b,{span:5},{default:s(()=>[t(S,{label:"推荐"},{default:s(()=>[t(W,{clearable:"",placeholder:"请选择推荐状态",modelValue:m.value.recommendType,"onUpdate:modelValue":r[2]||(r[2]=f=>m.value.recommendType=f)},{default:s(()=>[t(P,{value:0,label:"未推荐"}),t(P,{value:1,label:"已推荐"})]),_:1},8,["modelValue"])]),_:1})]),_:1}),t(b,{span:4,style:{paddingLeft:"10px"}},{default:s(()=>[t(Q,{type:"success",onClick:r[3]||(r[3]=f=>y())},{default:s(()=>r[4]||(r[4]=[L("查询 ")])),_:1})]),_:1})]),_:1})]),_:1},8,["model"])]),_:1})]),t(M,{class:"table-data-card"},{default:s(()=>[t(ee,{ref_key:"tableInfoRef",ref:g,columns:$,fetch:y,dataSource:v.value,options:_.value,extHeight:_.value.extHeight},{videoCover:s(({index:f,row:i})=>[e("div",Re,[t(Z,{source:i.videoCover,width:160},null,8,["source"]),e("div",Se,n(K(c).Utils.convertSecondsToHMS(i.duration)),1)])]),videoName:s(({index:f,row:i})=>[e("div",Te,[e("div",Ae,n(i.videoName),1),e("div",De,n(i.nickName),1),e("div",Le,[e("span",Pe,n(i.playCount),1),e("span",ze,n(i.likeCount),1),e("span",Oe,n(i.danmuCount),1),e("span",Ue,n(i.commentCount),1),e("span",je,n(i.coinCount),1),e("span",qe,n(i.collectCount),1)])])]),statusName:s(({row:f,index:i})=>[e("span",{style:se({color:p[f.status]})},n(f.statusName),5)]),recommendType:s(({row:f,index:i})=>[L(n(f.recommendType==1?"已推荐":"未推荐"),1)]),slotOperation:s(({index:f,row:i})=>[e("div",He,[e("a",{class:"a-link",href:"javascript:void(0)",onClick:j(O=>d(i),["prevent"])},"详情",8,Me),t(z,{direction:"vertical"}),i.status==2?(k(),I(D,{key:0},[e("a",{class:"a-link",href:"javascript:void(0)",onClick:O=>h(i)},"审核",8,Fe),t(z,{direction:"vertical"})],64)):T("",!0),i.status==3?(k(),I(D,{key:1},[e("a",{class:"a-link",href:"javascript:void(0)",onClick:O=>R(i)},n(i.recommendType==0?"推荐":"取消推荐"),9,Be),t(z,{direction:"vertical"})],64)):T("",!0),e("a",{class:"a-link",href:"javascript:void(0)",onClick:j(O=>l(i),["prevent"])},"删除",8,Ke)])]),_:1},8,["dataSource","options","extHeight"])]),_:1}),t(xe,{ref_key:"videoDetailRef",ref:C},null,512),t(le,{ref_key:"auditRef",ref:V,onReload:y},null,512)],64)}}},yt=J(Ee,[["__scopeId","data-v-3ca3071d"]]);export{yt as default};
