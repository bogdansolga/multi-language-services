from common.dto.order import OrderDTO
from flask import Blueprint, current_app, jsonify, request

order_blueprint = Blueprint("orders", __name__, url_prefix="/order")


@order_blueprint.route("/", methods=["POST"])
def create_order():
    data = request.get_json()

    current_app.extensions["order_service"].create_order_rest(OrderDTO(**data))
    return jsonify({"message": "Order created successfully"}), 201


@order_blueprint.route("/<int:customer_id>/<int:order_id>", methods=["GET"])
def charge_order(customer_id, order_id):
    current_app.extensions["order_service"].charge_order(customer_id, order_id)
    return jsonify({"message": "Order charged successfully"}), 200
