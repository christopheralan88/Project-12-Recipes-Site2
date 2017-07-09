$('#add-ingredient').on('click', function () {
    console.log('Button was pressed');
    var newIndex = $( '.ingredient-row' ).length;

    var html = '<div class="ingredient-row" id="ingredientNumber' + newIndex + '">' +
                    '<div class="prefix-20 grid-30">' +
                    '<input type="hidden" value="' + newIndex + '" id="ingredients' + newIndex +'.id}" name="ingredients[' + newIndex + '].id"/>' +
                        '<p>' +
                            '<input id="ingredients' + newIndex + '.name" name="ingredients[' + newIndex + '].name">' +
                        '</p>' +
                    '</div>' +
                    '<div class="grid-30">' +
                        '<p>' +
                            '<input id="ingredients' + newIndex + '.condition}" name="ingredients[' + newIndex + '].condition}"/>' +
                        '</p>' +
                    '</div>' +
                    '<div class="grid-10 suffix-10">' +
                        '<p>' +
                            '<p><input id="ingredients' + newIndex + '.measure" name="ingredients['+ newIndex + '].measure"></p>' +
                        '</p>' +
                    '</div>' +
                '</div>';

    $( "#addIngredientButton" ).before(html);
});
