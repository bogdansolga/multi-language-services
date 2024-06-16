# controllers.py

from flask import Blueprint, current_app, jsonify, make_response

billing_blueprint = Blueprint("billing", __name__, url_prefix="/billing")


@billing_blueprint.route("/<int:customer_id>/<int:order_id>", methods=["POST"])
def charge_customer(customer_id, order_id):
    result = current_app.get("billing_service").charge_customer(customer_id, order_id)
    if "not" in result:
        return make_response(jsonify({"error": result}), 400)
    return jsonify({"message": "Customer charged successfully"}), 200


@billing_blueprint.route("/<int:customer_id>", methods=["GET"])
def get_payments_for_customer(customer_id):
    # Implement logic to retrieve payments for customer
    payments = []  # Replace with actual payments data
    return jsonify(payments), 200
