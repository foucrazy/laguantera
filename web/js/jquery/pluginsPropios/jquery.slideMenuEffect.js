(function($) {

	$.fn.slideMenuEffect= function(options) {

		options = $.extend({
			//Class that is asigned to li that will moved
			selectedClassBox : 'CajaOpcionSeleccionada',
                        //Class that indicate which element of the menu is selected
                        elementSeletedId : 'elementoSeleccionado',
                        //Boolean that indicate if we are using hash o class to find the element selected in the menu
                        //By default we are using hash to locate the seleted element in the menu
                        useHash : true,
			//Animation's speed
			speed : 500,
			//Animation's easing efect
			easing : 'easeOutExpo'
		}, options);

		return this.each(function() {
			//Hold a reference to the menu
		 	var menu = $(this),
				//Hold a reference to de elemento to animate
		 		box = $("."+options.selectedClassBox,menu),
				element;
				
			//Set the default height for the box
			box.height(menu.height()+8);
			
			//Set the defaul top
			box.css({ "top": menu.position().top-4 + "px"});
		
                        if(options.useHash){
                            //Get the selected element with the hash, but if there is no hash we select the first element of menu
                            if(window.location.hash!=""){
                                    element = $('a[href="'+window.location.hash+'"]',menu).parent();
                            }else{
                                    element = $("ul > li:first",menu);
                            }
                        }else{
                            element = $('#'+options.elementSeletedId);
                        }
			
			//Animate to the previous element
			animateElement(box,element);
			
			//Select all the menu's element that don't have the selected class 
			$('li:not(.'+options.selectedClassBox+')', menu).hover(function(){
					//Hold a reference to li entered
					var selectedElement = $(this);
					
					//Animate to the selected element in the menu
					animateElement(box,selectedElement);
				}
				//call back for mouse hover where we came back to the previous state
				//In this case we will use the hash from the URL
				,function (){
					var previousElement;
					//Get the selected element with the hash, but if there is no hash we select the first 
					//element of menu
                                        
					if(options.useHash){
                                            //Get the selected element with the hash, but if there isn't no hash we select the first element of menu
                                            if(window.location.hash!=""){
                                                    previousElement = $('a[href="'+window.location.hash+'"]',menu).parent();
                                            }else{
                                                    previousElement = $("ul > li:first",menu);
                                            }
                                        }else{
                                            previousElement = $('#'+options.elementSeletedId);
                                        }
					
					//Animate to the previous element
					animateElement(box,previousElement);
				}
			);

		}); // end each
		
		
		//Function that animate the element
		function animateElement(box,selectedElement){
				var leftPosition = 0,//Left position of the elemenent entered, by default it 0.
                                    elementWidth = 0;//width position of the elemenent entered, by default it 0.
					
				//If the selected element is in the menu, we get the element's left position and width
				//But if the element isn't in the menu we animate the box outside of the element.
				if(selectedElement.length){
					leftPosition = selectedElement.position().left;
					elementWidth = selectedElement.width();
				}
				
				//Animate the element to the selected element position
				box.animate(
					{
						'left' : leftPosition,
						'width' : elementWidth
					},
					{
						duration : options.speed,
						easing : options.easing,
						queue : false
					}
				 );
		}

	};

})(jQuery);