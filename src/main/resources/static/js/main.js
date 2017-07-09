$('#addIngredientButton').on('click', function () {
    console.log('Button was pressed');

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
