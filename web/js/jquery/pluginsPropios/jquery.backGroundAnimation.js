(function($) {

	$.fn.backGroundAnimation= function(options) {
            
            var methods = {
                backEvent : function (content, extraElementHeight,selectedHash){
                    //Fade out te current div
                    content.find('#'+hash)
                                    .stop(true,false)
                                    .fadeOut(options.speedFadeAnimation, 
                                            function () {

                                                    $(this).addClass(options.divClassFadeOut);
                                                }
                                        );
                    //Animate to the select hash
                    methods[ "animateDivAndContent" ].call(this,content,extraElementHeight,selectedHash);
                },
                animateDivAndContent : function (content, extraElementHeight, divHash){
                    //Get the height of the selected div
                    var selecteDivHeight = content.find('#'+divHash).outerHeight() + extraElementHeight;

                    //If the height of the selected DIV is greater than minimun height should have the content div to use all the screen 
                    var finalHeight = (bodyHeight>selecteDivHeight)?(bodyHeight):(selecteDivHeight);

                    //Move the image back ground position to the next position
                    content.stop(true,false)
                                    .animate({'height':finalHeight},parseInt(options.speedHeight))
                                    .animate({'background-position': jSonProperties[divHash]},parseInt(options.speedBackgroudAnimation))
                                    .find('#'+divHash)
                                    .addClass(options.divClassFadeIn)
                                    .delay(parseInt(options.speedBackgroudAnimation))
                                    .fadeIn(parseInt(options.speedFadeAnimation), 
                                                    function () {
                                                                    $(this).removeClass(options.divClassFadeOut);										
                                                                }
                                                    );
                }
            }

            options = $.extend({
                    //If there is no hash, we will use this paramter to show a content
                    defaultElement : 'principal',
                    //Deafuls background Position from we start to animate first time
                    defaultElementPosition : '0px 0px',
                    //JSon default
                    jSonBackGroundPosition : '{"principal":"0px 0px"}',
                    //Wrap div that hold all the content to show
                    defaultContentId : 'content',
                    //Class we use when hide a div
                    divClassFadeOut : 'hidden',
                    //Class we use when show a div
                    divClassFadeIn : 'display',
                    //Id's element that are at the same level as the div who have all content (defaultContentId)
                    divs : ["header","menu","footer"],
                    //Enable or disable the history mode. 1 if it enabel or 0 if it disable
                    historyModeEnable : '1',
                    //Speed for the back ground animation
                    speedBackgroudAnimation : 1000,
                    //Speed for the height animation
                    speedHeight : 1000,
                    //Speed for the fade Effect
                    speedFadeAnimation : 1000
            }, options);

            //Get the back ground position as a object
            var jSonProperties = $.parseJSON(options.jSonBackGroundPosition);

            //Globals variables for all the function
            var content = $('#'+options.defaultContentId);

            //Set the backgroudPosition css inline
            content.css({backgroundPosition: options.defaultElementPosition});

            //Get the screen's height that we can use to full fit width element without scroll
            var bodyHeight = $('body').height();

            //Calculate the element's height that isn't where the conetent be displayed
            var extraElementHeight = 0;
            $.each(options.divs,function(index,value){
                    extraElementHeight += $("#"+value).outerHeight();
            });

            //Get the hash in the URL or set the default hash element
            var hash = window.location.hash.replace("#","") || options.defaultElement;

            //Set the image's position at first load by animated if from a default position and also animate the height
            methods["animateDivAndContent"].call(this,content,extraElementHeight,hash);
            
            //enable the history
            if(options.historyModeEnable) {
                $(window).bind('hashchange', function () {
                    var selectedHash = window.location.hash.replace("#","") || options.defaultElement;

                    methods["backEvent"].call(this,content,extraElementHeight,selectedHash);
                    
                    $('a[href="#'+selectedHash+'"]').parent().mouseover();
                    
                    hash = selectedHash;
                });

                $(window).trigger( "hashchange" );
            }

            return this.each(function (){
                    //Get all the links that have a hash and only a hash
                    $('a[href^="#"]').click(function (){
                            //Get hash from clicked link
                            var selectedHash = $(this).attr('href').replace("#","");

                            //Get the current hash
                            //hash = window.location.hash.replace("#","") || options.defaultElement;
                            if(hash!=selectedHash){
                                
                                    //Call the function that start the animation
                                    methods["backEvent"].call(this,content,extraElementHeight,selectedHash);
                                 
                                    //Set the selectHash to the current hash
                                    hash = selectedHash;
                            }else if (hash==selectedHash){
                                    return false;
                            }

                    });
                });
	};

})(jQuery);