### Python projects


## One-Time Setup

1. Create a virtual environment using Python 3:

    ```bash
    python3 -m venv venv
    ```

2. Activate the virtual environment:

    ```bash
    source venv/bin/activate
    ```

3. Install the required dependencies from `requirements.txt`:

    ```bash
    pip3 install -r requirements.txt
    ```

## Usage

This Python script is used to run different services based on the service name provided as a command-line argument.

```bash
python3 run.py <service_name>
```

Replace <service_name> with the name of the service you want to run. Currently supported services are "billing" and "order".



### Example

To run the billing service: 

```bash
python3 src/run.py billing
```

To run the order service: 

```bash
python3 src/run.py order
```