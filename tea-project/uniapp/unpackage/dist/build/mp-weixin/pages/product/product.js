(global["webpackJsonp"]=global["webpackJsonp"]||[]).push([["pages/product/product"],{"1de3":function(t,e,n){"use strict";n.r(e);var i=n("ce79"),o=n.n(i);for(var u in i)["default"].indexOf(u)<0&&function(t){n.d(e,t,(function(){return i[t]}))}(u);e["default"]=o.a},"36fb":function(t,e,n){"use strict";var i=n("6b57"),o=n.n(i);o.a},"380b":function(t,e,n){"use strict";(function(t,e){var i=n("47a9");n("496b");i(n("3240"));var o=i(n("86ed"));t.__webpack_require_UNI_MP_PLUGIN__=n,e(o.default)}).call(this,n("3223")["default"],n("df3c")["createPage"])},"5f4b":function(t,e,n){"use strict";n.d(e,"b",(function(){return i})),n.d(e,"c",(function(){return o})),n.d(e,"a",(function(){}));var i=function(){var t=this.$createElement;this._self._c},o=[]},"6b57":function(t,e,n){},"86ed":function(t,e,n){"use strict";n.r(e);var i=n("5f4b"),o=n("1de3");for(var u in o)["default"].indexOf(u)<0&&function(t){n.d(e,t,(function(){return o[t]}))}(u);n("36fb"),n("d9a6");var c=n("828b"),a=Object(c["a"])(o["default"],i["b"],i["c"],!1,null,null,null,!1,i["a"],void 0);e["default"]=a.exports},ce79:function(t,e,n){"use strict";(function(t){Object.defineProperty(e,"__esModule",{value:!0}),e.default=void 0;var i=n("b57a"),o={data:function(){return{index:0,swiperList:[],typeList:[],productList:[]}},onLoad:function(){this.getSwiperImageList(),this.getTypeList(),this.queryProductByType(1)},methods:{getSwiperImageList:function(){var t=this;(0,i.queryImageByType)(3).then((function(e){console.log(e.data),t.swiperList=e.data}))},getTypeList:function(){var t=this;(0,i.getTypes)().then((function(e){t.typeList=e.data}))},selectTab:function(t,e){this.index=t,this.queryProductByType(e)},queryProductByType:function(t){var e=this;(0,i.queryProductByType)(t).then((function(t){e.productList=t.data}))},addCart:function(e){console.log("购物车"),console.log(e),(0,i.addCart)(e.id+"&tprice="+e.tprice).then((function(e){console.log(e),200===e.code?t.showToast({title:"添加成功",duration:2e3}):t.showToast({title:e.data.msg,duration:2e3,icon:"error"})}))}}};e.default=o}).call(this,n("df3c")["default"])},d9a6:function(t,e,n){"use strict";var i=n("fc34"),o=n.n(i);o.a},fc34:function(t,e,n){}},[["380b","common/runtime","common/vendor"]]]);