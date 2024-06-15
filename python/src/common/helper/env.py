import os


def add_config_to_env(app):
    with app.app_context():
        for key, value in app.config.items():
            os.environ[key] = str(value)
