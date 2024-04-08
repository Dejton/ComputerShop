// add_to_cart.js

function addToCart(element) {
    var productId = element.closest('.card').find('.productId').val();
    var quantity = 1;
    $.post('/addToCart', {productId: productId, quantity: quantity}, function(response) {
        alert(response);
        // Aktualizuj widok koszyka
    });
}
