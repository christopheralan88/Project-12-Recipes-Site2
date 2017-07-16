$('#addIngredientButton').on('click', function () {
    console.log('Ingredient button was pressed');

    var newValue = $( '.ingredient-row' ).length + 1;
    var newIndex = $( '.ingredient-row' ).length;

    var html = '<div class="ingredient-row" >' +
                    '<div class="prefix-20 grid-30">' +
                    '<input type="hidden" id="ingredients' + newIndex +'.id" name="ingredients[' + newIndex + '].id"/>' +
                        '<p>' +
                            '<input id="ingredients' + newIndex + '.name" name="ingredients[' + newIndex + '].name"/>' +
                        '</p>' +
                    '</div>' +
                    '<div class="grid-30">' +
                        '<p>' +
                            '<input id="ingredients' + newIndex + '.condition" name="ingredients[' + newIndex + '].condition"/>' +
                        '</p>' +
                    '</div>' +
                    '<div class="grid-10 suffix-10">' +
                        '<p>' +
                            '<input id="ingredients' + newIndex + '.measure" name="ingredients['+ newIndex + '].measure"/>' +
                        '</p>' +
                    '</div>' +
                '</div>';

    $( "#addIngredientButton" ).closest('div').before(html);
});


$('#addInstructionButton').on('click', function () {
    console.log('Instruction button was pressed');

    var newIndex = $( '.step-row' ).length;

    var html = '<div class="step-row">' +
                   '<div class="prefix-20 grid-30">' +
                   '<input type="hidden" id="instructions' + newIndex + '.id" name="instructions[' + newIndex + '].id"/>' +
                          '<p>' +
                               '<input id="instructions' + newIndex + '.step" name="instructions[' + newIndex + '].step"/>' +
                          '</p>' +
                   '</div>' +
                   '<div class="grid-30">' +
                         '<p>' +
                            '<textarea id="instructions' + newIndex + '.text" name="instructions[' + newIndex + '].text"></textarea>' +
                         '</p>' +
                   '</div>' +
                '</div>'

    $( "#addInstructionButton" ).closest('div').before(html);
});
