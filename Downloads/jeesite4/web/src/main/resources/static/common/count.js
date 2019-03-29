;(function($,window,document,undefined){
  $.fn.iVaryVal=function(iSet,CallBack){
        /*
         * Minus:点击元素--减小
         * Add:点击元素--增加
         * Input:表单元素
         * Min:表单的最小值，非负整数
         * Max:表单的最大值，正整数
         */
         iSet=$.extend({},{Minus:$('.J_minus'),Add:$('.J_add'),Input:$('.J_input'),Min:0,Max:20},iSet);
        // 把所有元素都绑在isSet对象中
        var C=null,O=null; 
        // O input中的值
        //插件返回值
        var $CB={};
        //增加
        iSet.Add.each(function(i){
            $(this).click(function(){
                O=parseInt(iSet.Input.eq(i).val());
                (O+1<=iSet.Max) || (iSet.Max==null) ? iSet.Input.eq(i).val(O+1) : iSet.Input.eq(i).val(iSet.Max);
                        //输出当前改变后的值
                        $CB.val=iSet.Input.eq(i).val();
                        $CB.index=i;
                        test(this, $CB.val);
                        //回调函数
                        if (typeof CallBack == 'function') {
                            CallBack($CB.val,$CB.index);
                        }
                    });
        });
        //减少
        iSet.Minus.each(function(i){
            $(this).click(function(){
                O=parseInt(iSet.Input.eq(i).val());
                O-1<iSet.Min ? iSet.Input.eq(i).val(iSet.Min) : iSet.Input.eq(i).val(O-1);
                $CB.val=iSet.Input.eq(i).val();
                $CB.index=i;
                test(this, $CB.val);
                        //回调函数
                        if (typeof CallBack == 'function') {
                            CallBack($CB.val,$CB.index);
                        }
                    });
        });
        //手动
        iSet.Input.bind({
            'click':function(){
                O=parseInt($(this).val());
                $(this).select();
            },
            'keyup':function(e){
                if($(this).val()!=''){
                    C=parseInt($(this).val());
                                //非负整数判断
                                if(/^[1-9]\d*|0$/.test(C)){
                                    $(this).val(C);
                                    O=C;
                                }else{
                                    $(this).val(O);
                                }
                            }
                        //键盘控制：上右--加，下左--减
                        if(e.keyCode==38 || e.keyCode==39){
                            iSet.Add.eq(iSet.Input.index(this)).click();
                        }
                        if(e.keyCode==37 || e.keyCode==40){
                            iSet.Minus.eq(iSet.Input.index(this)).click();
                        }
                        //输出当前改变后的值
                        $CB.val=$(this).val();
                        $CB.index=iSet.Input.index(this);
                        //回调函数
                        test(this, $CB.val);
                        if (typeof CallBack == 'function') {
                            CallBack($CB.val,$CB.index);
                        }
                    },
                    'blur':function(){
                        $(this).trigger('keyup');
                        if($(this).val()==''){
                            $(this).val(O);
                        }
                        //判断输入值是否超出最大最小值
                        if(iSet.Max){
                            if(O>iSet.Max){
                                $(this).val(iSet.Max);
                            }
                        }
                        if(O<iSet.Min){
                            $(this).val(iSet.Min);
                        }
                        //输出当前改变后的值
                        $CB.val=$(this).val();
                        $CB.index=iSet.Input.index(this);
                        test(this, $CB.val);
                        //回调函数
                        if (typeof CallBack == 'function') {
                            CallBack($CB.val,$CB.index);
                        }
                    }
                });
        function test (selector,c) {
         var children=$(selector).parent().children();
         if (c==0) {

            children.eq(0).removeClass("no-zero-left").addClass("zero-left");
            children.eq(2).removeClass("zero-right").addClass("no-zero-right");
        }else if (c== iSet.Max) {
            children.eq(0).removeClass("zero-left").addClass("no-zero-left");
            children.eq(2).removeClass("no-zero-right").addClass("zero-right");
        }else{
            children.eq(0).removeClass("zero-left").addClass("no-zero-left");
            children.eq(2).removeClass("zero-right").addClass("no-zero-right");
        }
    }
}



})(jQuery,window,document);