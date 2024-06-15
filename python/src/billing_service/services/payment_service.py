from random import randint

from common.dto.order import OrderChargingStatusDTO


class PaymentService:
    # invokes a payment gateway (ex: Braintree) and returns the charging result
    def charge(self, payment_method_id: int, amount: float) -> OrderChargingStatusDTO:
        return OrderChargingStatusDTO(
            successful=bool(randint(0, 1)),
            failure_reason="The card has expired" if randint(0, 1) == 0 else None,
        )
