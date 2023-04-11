package com.food.factory.User.Model

class OrderModel(
    var userId: String? = null,
    var orderId: String? = null,
    var paymentId: String? = null,
    var paymentPrice: String? = null,
    var status: String? = null,
    var listener: ArrayList<CartModel>? = null
                ) {
}