import{l as H,o as i,c as g,I as h,Q as v,a as o,P as u,H as c,D as p,u as e,O as m,a4 as M,G as _,L,M as S,C as N,K as j,i as A,ag as I,b as k,j as q,S as U,e as G}from"./@vue-JlwsckWB.js";import{_ as B,u as E,a as Q,m as R}from"./index-D9sq5B9z.js";import{_ as Y}from"./Account-BeQtjicT.js";import{u as J}from"./navActionStore-yUvuvR1j.js";import{a as F,u as V}from"./vue-router-hI8gbx6m.js";import{L as W}from"./LayoutHeader-Bc0V7Q4q.js";import"./pinia-91x-qzso.js";import"./vue-demi-Dq6ymT-8.js";import"./element-plus-BCXmXNOW.js";import"./lodash-es-DpRhkxuT.js";import"./@vueuse-DS8A98-V.js";import"./@element-plus-Dbr4-WMN.js";import"./@popperjs-D9SI2xQl.js";import"./@ctrl-r5W6hzzQ.js";import"./dayjs-Cvt7qOjS.js";import"./artplayer-DYEsUqgO.js";import"./async-validator-DKvM95Vc.js";import"./memoize-one-BdPwpGay.js";import"./normalize-wheel-es-B6fDCfyv.js";import"./@floating-ui-DVIT0l11.js";import"./axios-upsvKRUO.js";import"./vue-cookies-DmNBPvI0.js";import"./@fingerprintjs-BlNouByb.js";import"./tslib-BLN2B0TR.js";import"./vue-cropper-BAfMpYow.js";import"./mitt-DJ65BbbF.js";import"./moment-C5S46NFB.js";import"./js-md5-Ddb6WJu6.js";const X={class:"category"},Z={class:"child-list"},ee={class:"child-list"},te={__name:"Category",props:{showType:{type:Number,default:0},mouseOver:{type:Boolean,default:!1}},setup(f){const t=E(),{proxy:n}=A();F();const w=V(),s=f,b=H(()=>{let y=t.categoryList.length;return t.categoryList.length>n.rowCategoryCount*2&&(y=n.rowCategoryCount*2-1),y});return H(()=>s.showType==0||s.mouseOver?"":"category-list-out"),(y,d)=>{const l=I("router-link"),$=I("el-popover");return i(),g(m,null,[h(o("div",X,[u(l,{class:"hot",to:"/hot",target:"_blank"},{default:c(()=>d[0]||(d[0]=[o("div",{class:"iconfont icon-hot"},null,-1),o("div",{class:"info"},"热门",-1)])),_:1}),o("div",{class:"category-list",style:p({"grid-template-columns":`repeat(${e(n).rowCategoryCount}, 1fr)`})},[(i(!0),g(m,null,M(b.value,a=>(i(),g(m,null,[e(t).categoryList[a-1].children&&e(t).categoryList[a-1].children.length>0?(i(),_($,{key:0,width:187,trigger:"hover","show-arrow":!1,offset:5,placement:a<=y.rowCategoryCount?"top":"bottom"},{reference:c(()=>[u(l,{class:N(["category-item",e(t).categoryList[a-1].categoryCode==e(w).params.pCategoryCode?"active":""]),to:`/v/${e(t).categoryList[a-1].categoryCode}`},{default:c(()=>[L(S(e(t).categoryList[a-1].categoryName),1)]),_:2},1032,["class","to"])]),default:c(()=>[o("div",Z,[(i(!0),g(m,null,M(e(t).categoryList[a-1].children,C=>(i(),_(l,{class:"child",title:C.categoryName,to:`/v/${e(t).categoryList[a-1].categoryCode}/${C.categoryCode}`},{default:c(()=>[L(S(C.categoryName),1)]),_:2},1032,["title","to"]))),256))])]),_:2},1032,["placement"])):(i(),_(l,{key:1,class:N(["category-item",e(t).categoryList[a-1].categoryCode==e(w).params.pCategoryCode?"active":""]),to:`/v/${e(t).categoryList[a-1].categoryCode}`},{default:c(()=>[L(S(e(t).categoryList[a-1].categoryName),1)]),_:2},1032,["class","to"]))],64))),256)),e(t).categoryList.length>e(n).rowCategoryCount*2?(i(),_($,{key:0,width:187,trigger:"hover","show-arrow":!1,offset:5,placement:"bottom-end"},{reference:c(()=>d[1]||(d[1]=[o("div",{class:"category-item btn-category-more"},[L(" 更多 "),o("span",{class:"iconfont icon-more"})],-1)])),default:c(()=>[o("div",ee,[(i(!0),g(m,null,M(e(t).categoryList.slice(e(n).rowCategoryCount*2-1,e(t).categoryList.length),a=>(i(),_(l,{class:"child",to:`/v/${a.categoryCode}`,target:"_blank"},{default:c(()=>[L(S(a.categoryName),1)]),_:2},1032,["to"]))),256))])]),_:1})):j("",!0)],4)],512),[[v,f.showType==0]]),h(o("div",{class:N(["category",f.mouseOver?"":"category-out"])},[u(l,{class:"hot hot-out",to:"/hot",target:"_blank"},{default:c(()=>d[2]||(d[2]=[o("div",{class:"iconfont icon-hot"},null,-1),o("div",{class:"info"},"热门",-1)])),_:1}),o("div",{class:"category-list",style:p({"grid-template-columns":`repeat(${e(n).rowCategoryCount}, 1fr)`})},[(i(!0),g(m,null,M(e(t).categoryList,a=>(i(),_(l,{class:"category-item",to:`/v/${a.categoryCode}`},{default:c(()=>[L(S(a.categoryName),1)]),_:2},1032,["to"]))),256))],4),h(o("div",{class:N(["category-op iconfont",f.mouseOver?"icon-up":"icon-down"])},null,2),[[v,e(t).categoryList.length>e(n).rowCategoryCount]])],2),[[v,f.showType==1]])],64)}}},z=B(te,[["__scopeId","data-v-9d4d1ead"]]),oe={class:"category-fixed-inner"},ae={class:"category"},re={class:"body-main"},se={__name:"Layout",setup(f){const t=J(),n=Q(),w=E(),{proxy:s}=A();F(),V(),k([{name:"首页",path:"/"},{name:"板块",path:"/fishing"}]);const b=k(!1),y=r=>{r==1?b.value=!0:b.value=!1},d=k(0),l=k(!1),$=r=>{r<=20?d.value=!1:d.value=!0,r>250?l.value=!0:l.value=!1};q(()=>{window.addEventListener("scroll",a),window.addEventListener("resize",C)}),U(()=>{window.removeEventListener("scroll",a),window.removeEventListener("resize",C)});const a=()=>{var r=window.scrollY;$(r),R.emit("windowScroll",r)},C=()=>{R.emit("windowResize")};w.categoryMap;const O=H(()=>{const r=w.cureentPCategory?w.cureentPCategory.background:null;return r?s.Api.sourcePath+r:null}),T=k([]);(async()=>{let r=await s.Request({url:s.Api.getSearchKeywordTop});r&&(T.value=r.data)})();const K=async()=>{if(Object.keys(n.userInfo).length==0)return;let r=await s.Request({url:s.Api.getNoReadCount});r&&n.saveMessageNoReadCount(r.data)};return G(()=>n.userInfo,(r,x)=>{r&&K()},{immediate:!0,deep:!0}),(r,x)=>{const P=I("router-view");return i(),g(m,null,[o("div",{class:"main-container",style:p({"max-width":e(s).bodyMaxWidth+"px","min-width":e(s).bodyMinWidth+"px"})},[h(o("div",{class:"header",style:p({"background-image":`url(${O.value?O.value:e(s).Utils.getLocalImage("banner_bg.png")})`})},[u(W,{theme:"light",hotSearchList:T.value},null,8,["hotSearchList"])],4),[[v,e(t).showHeader]]),h(o("div",{class:"header-fixed",style:p({"max-width":e(s).bodyMaxWidth+"px","min-width":e(s).bodyMinWidth+"px"})},[u(W,{theme:"dark",hotSearchList:T.value},null,8,["hotSearchList"])],4),[[v,e(t).fixedHeader&&d.value||e(t).forceFixedHeader]]),h(o("div",{class:"category-fixed",style:p({"max-width":e(s).bodyMaxWidth+"px","min-width":e(s).bodyMinWidth+"px"}),onMouseover:x[0]||(x[0]=D=>y(1)),onMouseout:x[1]||(x[1]=D=>y(0))},[o("div",oe,[u(z,{showType:1,mouseOver:b.value},null,8,["mouseOver"])])],36),[[v,e(t).fixedCategory&&l.value]]),o("div",{class:"body-container",style:p({"max-width":e(s).bodyMaxWidth+"px","min-width":e(s).bodyMinWidth+"px"})},[h(o("div",ae,[u(z,{showType:0})],512),[[v,e(t).showCategory]]),o("div",re,[u(P)])],4)],4),u(Y)],64)}}},We=B(se,[["__scopeId","data-v-96e73477"]]);export{We as default};
