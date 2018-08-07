var popMenu = {
    
    MajMenuHeight : 55, // height of a major menu
    MinMenuHeight : 25, // height of a minor menu
    logoHeight : 75, // logo height
    MenuOpen: false,
    
    /*
     * Name:        getMinorMenu
     * Purpose:     To set up a minor menu tag with a name.
     * Arguments:   Name (label) of the menu.
     * Output:      --
     * Modifies:    --
     * Returns:     The full tag, with inset data and functions.
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
    getMajorMenu: function(name, majs, mins, cins)
    {
        return "<MajorMenu onclick='popMenu.setMenuSelect(" + majs + ", " + mins + ", " + cins +");'>" 
                + name + "</MajorMenu>";
    },

    /*
     * Name:        getMinorMenu
     * Purpose:     To set up a minor menu tag with a name.
     * Arguments:   Name (label) of the menu.
     * Output:      --
     * Modifies:    --
     * Returns:     The full tag, with inset data and functions.
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
    getMinorMenu: function(name, majs, mins, cins)
    {
        return "<MinorMenu onclick='popMenu.setMenuSelect(" + majs + ", " + mins + ", " + cins +");'>"
                + name + "</MinorMenu>";
    },

    /*
     * Name:        loadPopMenu() 
     * Purpose:     To load a json file formated for the major and minor
     *              menu sections. 
     * Arguments:   The file path. (url)
     * Output:      --
     * Modifies:    All pop menu tags will gain the major and minor
     *              menus specified, and the menu color.
     *              Sets the menus close and open function
     * Returns:     --
     * Assumptions: That your file follows the specified format.
     * Bugs:        --
     * Notes:       Structure:
                    {
                        MajorMenus:[
                            {
                                "Label":"Major menu label",
                                "MinorMenus":[
                                    {
                                        "Label":"Animations",
                                        "Position": "Scroll location on click",
                                    },
                                    {
                                        "                  "
                                    },
                                    ...
                                ],
                                "Position": "Scroll location on click",
                                "Length": "Number of minor menus in this major menu"
                            },
                            {
                                "                                                  "
                            },
                            ...
                        ],
                        Length: "size of major Menus",
                        Color: {
                            "Red": "amount of red the menu has",
                            "Green": "amount of green the menu has",
                            "Blue: "amount of blue the menu has"
                        }
                    }
     */
        
    loadPopMenu: function(fileName)
    {
        $.ajax({
            type: "GET",
            url: fileName,
            dataType: "json",
            success: function(data) {

                popMenu.setPopMenuBackground(data.Color.Red, data.Color.Green, data.Color.Blue);
                
                var majors = 0;
                var minors = 0;
                var countin = 0;

                // Places this to clear and set the menu. This makes it easier to change
                // the menu contents. Just need to rerun this function with another Json.
                $("popMenu").html("<Logo></Logo>\
                                   <backSlider></backSlider>\
                                   <backSliderTail></backSliderTail>");

                for (var i1 = 0; i1 < data.Length; i1++)
                {
                    majors++;
                    countin += data.MajorMenus[i1].Length;

                    // Set to first menu.
                    if (i1 == 0)
                        popMenu.setMenuSelect(majors, minors, countin);

                    $("popMenu").append(popMenu.getMajorMenu(data.MajorMenus[i1].Label, majors, minors, countin));
                    for (var i2 = 0; i2 < data.MajorMenus[i1].Length; i2++)
                    {
                        minors++;

                        $("popMenu").append(popMenu.getMinorMenu(data.MajorMenus[i1].MinorMenus[i2].Label,
                                                         majors, minors, countin));
                    }

                }

                // Set the Jquery call back from closing the menu on click.
                $("popMenuExpander").click(function()
                {
                    if (popMenu.MenuOpen)
                    {
                        $("popMenuExpander").animate({left: "5px"}, 200);
                        $("popMenu").animate({"left": "-245px"}, 200);
                    }
                    else
                    {
                        $("popMenuExpander").animate({left: "250px"}, 200);
                        $("popMenu").animate({"left": "0px"}, 200);
                    }
                    popMenu.MenuOpen = !popMenu.MenuOpen;
                });
                },
                error: function() {
                    console.error("Error could not find popMenu url:\n" +
                                 fileName + "\n");
                }
        });
    },


    /*
     * Name:        setMenuSelect
     * Purpose:     To set the current selected menu position.
     * Arguments:   The number of major menus passed, and
     *              number of minor menus passed, and the number
                    of minor menus in the current major menu.
     * Output:      --
     * Modifies:    Sets the current menu major, and minor
                    selection.
     * Returns:     --
     * Assumptions: --
     * Bugs:        --
     * Notes:       This should be set up automatically from loadPopMenu
     */
    setMenuSelect: function(majs, mins, countIn)
    {
        var heightCalc = majs * popMenu.MajMenuHeight + mins * popMenu.MinMenuHeight + popMenu.logoHeight;
        var tailLength = countIn * popMenu.MinMenuHeight - mins * popMenu.MinMenuHeight

        $("popMenu > backSlider").animate({height: heightCalc + "px"}, 200);
        $("popMenu > backSliderTail").animate({top: heightCalc + "px", height: tailLength + "px"}, 200);
    },

    /*
     * Name:        setPopMenuBackground
     * Purpose:     to set the background color of the pop out menu
     * Arguments:   red, blue and gree values.
     * Output:      --
     * Modifies:    Changes the color of the background and the hover values
     * Returns:     --
     * Assumptions: --
     * Bugs:        --
     * Notes:       --
     */
    setPopMenuBackground: function(red, green, blue)
    {
        $("popMenu").css({"background-color": "rgb(" + red + "," + green + "," + blue + ")",
                          "border-right-color": "rgb(" + red * 1.6 + "," + green * 1.6 + "," + blue * 1.6 + ")"});
        $("popMenuExpander").css({"background-color": "rgb(" + red + "," + green + "," + blue + ")"});
        
        var rgbHover = {red: red * 0.6 + 255 * 0.4, green: green * 0.6 + 255 * 0.4,
                        blue: blue * 0.6 + 255 * 0.4};
        
        $("popMenuExpander, popMenuExpander > i").stop().hover(function(){
            $("popMenuExpander").css({"background-color": "rgb(" 
                         + rgbHover.red + "," + rgbHover.green + "," + rgbHover.blue + ")"});
            
            $("popMenu").css({"border-right-color": "rgb(" + rgbHover.red + "," + rgbHover.green + "," + rgbHover.blue  + ")"});
        });
        $("popMenuExpander, popMenuExpander > i").stop().mouseout(function() {
            $("popMenuExpander").css({"background-color": "rgb(" + red + "," + green + "," + blue + ")"});
            $("popMenu").css({"border-right-color": "rgb(" + red * 1.6 + "," + green * 1.6 + "," + blue * 1.6 + ")"});
        });
    }
};