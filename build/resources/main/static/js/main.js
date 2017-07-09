$('#add-ingredient').on('click', function () {
    var newIndex = $( '.ingredient-row' ).length;

    var html = '<div class="ingredient-row" id="ingredientNumber' + newIndex + '">' +
                '<input type="hidden" id="ingredients' + newIndex +'.id}" name="ingredients[' + newIndex + '].name"/>' +
                    '<div class="prefix-20 grid-30">' +
                        '<p>' +
                            '<input id="ingredients' + newIndex + '.name" name="ingredients[' + newIndex + '].name">' +
                        '</p>' +
                    '</div>' +
                    '<div class="grid-10 suffix-10">' +
                        '<p>' +
                            '<p><input style="width: 80%" id="ingredients' + newIndex + '.measure" name="ingredients['+ newIndex + '].measure"></p>' +
                        '</p>' +
                    '</div>' +
                '</div>'

    $( "#addIngredientButton" ).before(html);
});
