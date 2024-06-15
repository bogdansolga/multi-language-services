from concurrent.futures import ThreadPoolExecutor


def run_async(func, *args, **kwargs):
    """
    Run the specified function asynchronously in a thread pool.

    Args:
        func: The function to run asynchronously.
        *args: Positional arguments to pass to the function.
        **kwargs: Keyword arguments to pass to the function.

    Returns:
        A Future object representing the result of the asynchronous operation.
    """
    executor = ThreadPoolExecutor(max_workers=1)
    return executor.submit(func, *args, **kwargs)
