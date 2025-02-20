import{i as r}from"./vue-demi-Dq6ymT-8.js";import{ao as p,b as l,an as u}from"./@vue-o39Kj8Hc.js";/*!
 * pinia v2.3.0
 * (c) 2024 Eduardo San Martin Morote
 * @license MIT
 */const f=Symbol();var i;(function(t){t.direct="direct",t.patchObject="patch object",t.patchFunction="patch function"})(i||(i={}));function b(){const t=p(!0),n=t.run(()=>l({}));let o=[],c=[];const a=u({install(e){a._a=e,e.provide(f,a),e.config.globalProperties.$pinia=a,c.forEach(s=>o.push(s)),c=[]},use(e){return!this._a&&!r?c.push(e):o.push(e),this},_p:o,_a:null,_e:t,_s:new Map,state:n});return a}export{b as c};
