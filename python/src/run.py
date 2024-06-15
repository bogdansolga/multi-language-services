import sys

from billing_service.app import run_billing_service
from order_service.app import run_order_service

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python run.py <service_name>")
        sys.exit(1)

    service_name = sys.argv[1]

    service_bindings = {
        "billing": run_billing_service,
        "order": run_order_service,
    }

    if service_name not in service_bindings:
        print("Unknown service: {}".format(service_name))
        sys.exit(1)

    service_bindings[service_name]()
