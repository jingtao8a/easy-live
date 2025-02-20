import{b as h,l as G,j as J,S as Q,o as a,c as l,a as e,P as d,H as r,L as M,O as g,a4 as I,u as o,G as S,K as _,M as c,V as W,C as O,I as X,ak as Y,a5 as Z,i as ee,ag as p}from"./@vue-JlwsckWB.js";import{_ as se,a as te,u as oe,e as ne,m as q}from"./index-D9sq5B9z.js";import{u as ae,a as le}from"./vue-router-hI8gbx6m.js";const ie={class:"menu"},re={class:"nav-list"},ue={class:"nav-part"},ce={key:0,class:"icon"},de=["src"],ve={class:"category-name"},fe={class:"search-body"},pe={class:"search-panel-inner"},me=["placeholder"],ye={key:0,class:"history-panel"},he={class:"search-title"},ge={class:"search-tag-list"},_e={class:"hot-search-list"},ke=["onClick"],Ce={class:"user-panel"},Ie={class:"user-avatar"},we={class:"user-info-panel"},be={class:"nick-name"},Se={class:"count-info"},$e={class:"count-info-item"},Le={class:"count-value"},He={class:"count-info-item"},Ne={class:"count-value"},Re={class:"count-info-item"},Ve={class:"count-value"},je={class:"btn-upload"},w=5,xe={__name:"LayoutHeader",props:{theme:{type:String,default:"light"},hotSearchList:{type:Array,default:[]}},setup($){const{proxy:v}=ee(),z=ae(),L=le(),n=te(),H=oe(),b=ne(),k=h({}),B=async()=>{let i=await v.Request({url:v.Api.getUserCountInfo});i&&(k.value=i.data)},N=G(()=>Math.ceil(H.categoryList.length/w)),K=()=>{if(Object.keys(n.userInfo).length==0){n.setLogin(!0);return}},R=h("jingtao8a"),m=h(),V=i=>{L.push({path:"/search",query:{keyword:i}})},j=()=>{m.value||(m.value=R.value),L.push({path:"/search",query:{keyword:m.value}})},f=h(!1),U=()=>{f.value=!0},x=h();J(()=>{document.addEventListener("click",()=>{f.value=!1}),q.on("windowScroll",()=>{f.value&&(x.value.blur(),f.value=!1)})}),Q(()=>{q.off("windowScroll")});const y=i=>{if(Object.keys(n.userInfo).length==0){n.setLogin(!0);return}window.open(i,"_blank")},F=()=>{v.Confirm({message:"确定要退出?",okfun:async()=>{await v.Request({url:v.Api.logout})&&n.saveUserInfo({})}})};return(i,s)=>{const C=p("router-link"),P=p("el-popover"),D=p("el-tag"),A=p("Avatar"),E=p("el-badge"),T=p("el-button");return a(),l("div",{class:O(["header-bar","header-bar-"+$.theme])},[e("div",ie,[d(P,{width:N.value*171+24,trigger:"hover","show-arrow":!1,offset:22,placement:"bottom-start"},{reference:r(()=>[d(C,{class:"iconfont icon-logo menu-item",to:"/"},{default:r(()=>s[8]||(s[8]=[M("首页")])),_:1})]),default:r(()=>[e("div",re,[(a(!0),l(g,null,I(N.value,t=>(a(),l("div",ue,[(a(!0),l(g,null,I(o(H).categoryList.slice((t-1)*w,(t-1)*w+w),u=>(a(),S(C,{class:"nav-item",to:`/v/${u.categoryCode}`},{default:r(()=>[u.icon?(a(),l("span",ce,[e("img",{src:`${o(v).Api.sourcePath}${u.icon}`},null,8,de)])):_("",!0),e("span",ve,c(u.categoryName),1)]),_:2},1032,["to"]))),256))]))),256))])]),_:1},8,["width"])]),e("div",fe,[o(z).path!="/search"?(a(),l("div",{key:0,class:"search-panel",onClick:s[2]||(s[2]=W(()=>{},["stop"]))},[e("div",pe,[e("div",{class:O(["input-panel",f.value?"focus-input":""])},[X(e("input",{onFocus:U,"onUpdate:modelValue":s[0]||(s[0]=t=>m.value=t),ref_key:"searchInputRef",ref:x,placeholder:R.value,onKeyup:Z(j,["enter"])},null,40,me),[[Y,m.value]]),e("div",{class:"iconfont icon-search",onClick:j})],2),f.value?(a(),l("div",ye,[e("div",he,[s[9]||(s[9]=e("div",{class:"title"},"搜索历史",-1)),e("div",{class:"btn-clean",onClick:s[1]||(s[1]=t=>o(b).cleanHistory())}," 清空 ")]),e("div",ge,[(a(!0),l(g,null,I(o(b).searchHistory,t=>(a(),S(D,{key:t.name,closable:"",type:"info",class:"search-tag",onClick:u=>V(t),onClose:u=>o(b).delHistory(t)},{default:r(()=>[M(c(t),1)]),_:2},1032,["onClick","onClose"]))),128))]),s[10]||(s[10]=e("div",{class:"hot-search-title"},"热搜",-1)),e("div",_e,[(a(!0),l(g,null,I($.hotSearchList,(t,u)=>(a(),l("div",{class:"search-item",onClick:Ae=>V(t)},c(u+1)+" "+c(t),9,ke))),256))])])):_("",!0)])])):_("",!0)]),e("div",Ce,[e("div",Ie,[Object.keys(o(n).userInfo).length>0?(a(),l(g,{key:0},[d(A,{class:"avatar",avatar:o(n).userInfo.avatar,userId:o(n).userInfo.userId,width:35,lazy:!1,onMouseover:B},null,8,["avatar","userId"]),e("div",we,[e("div",be,c(o(n).userInfo.nickName),1),e("div",Se,[e("div",$e,[e("div",Le,c(k.value.focusCount),1),s[11]||(s[11]=e("div",{class:"count-title"},"关注",-1))]),e("div",He,[e("div",Ne,c(k.value.fansCount),1),s[12]||(s[12]=e("div",{class:"count-title"},"粉丝",-1))]),e("div",Re,[e("div",Ve,c(k.value.currentCoinCount),1),s[13]||(s[13]=e("div",{class:"count-title"},"硬币",-1))])]),d(C,{to:`/user/${o(n).userInfo.userId}`,class:"item iconfont icon-user"},{default:r(()=>s[14]||(s[14]=[e("span",{class:"item-name"},"个人中心",-1),e("span",{class:"iconfont icon-left"},null,-1)])),_:1},8,["to"]),d(C,{to:"/ucenter/video",class:"item iconfont icon-play"},{default:r(()=>s[15]||(s[15]=[e("span",{class:"item-name"},"投稿管理",-1),e("span",{class:"iconfont icon-left"},null,-1)])),_:1}),e("div",{class:"logout item iconfont icon-logout",onClick:F}," 退出登录 ")])],64)):_("",!0),Object.keys(o(n).userInfo).length==0?(a(),S(A,{key:1,onClick:K,width:35,lazy:!1})):_("",!0)]),e("div",{class:"user-panel-item",onClick:s[3]||(s[3]=t=>y("/message"))},[d(E,{value:o(n).messageNoReadCount,hidden:o(n).messageNoReadCount==0},{default:r(()=>s[16]||(s[16]=[e("div",{class:"iconfont icon-message"},null,-1)])),_:1},8,["value","hidden"]),s[17]||(s[17]=e("div",null,"消息",-1))]),e("div",{class:"user-panel-item",onClick:s[4]||(s[4]=t=>y(`/user/${o(n).userInfo.userId}/collection`))},s[18]||(s[18]=[e("div",{class:"iconfont icon-collection"},null,-1),e("div",null,"收藏",-1)])),e("div",{class:"user-panel-item",onClick:s[5]||(s[5]=t=>y("/history"))},s[19]||(s[19]=[e("div",{class:"iconfont icon-history"},null,-1),e("div",null,"历史",-1)])),e("div",{class:"user-panel-item",onClick:s[6]||(s[6]=t=>y("/ucenter/home"))},s[20]||(s[20]=[e("div",{class:"iconfont icon-light"},null,-1),e("div",null,"创作中心",-1)])),e("div",je,[d(T,{type:"primary",onClick:s[7]||(s[7]=t=>y("/ucenter/postVideo")),size:"large"},{default:r(()=>s[21]||(s[21]=[e("span",{class:"iconfont icon-upload"},null,-1),e("span",null,"投稿",-1)])),_:1})])])],2)}}},ze=se(xe,[["__scopeId","data-v-8d798c94"]]);export{ze as L};
