PUI={zindex:1000,scrollInView:function(b,e){var h=parseFloat(b.css("borderTopWidth"))||0,d=parseFloat(b.css("paddingTop"))||0,f=e.offset().top-b.offset().top-h-d,a=b.scrollTop(),c=b.height(),g=e.outerHeight(true);
if(f<0){b.scrollTop(a+f)
}else{if((f+g)>c){b.scrollTop(a+f-c+g)
}}},isIE:function(a){return($.browser.msie&&parseInt($.browser.version,10)==a)
},escapeRegExp:function(a){return a.replace(/([.?*+^$[\]\\(){}|-])/g,"\\$1")
},escapeHTML:function(a){return a.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;")
}};