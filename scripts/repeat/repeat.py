import aiohttp
import asyncio
import time
from urllib import request

from aiohttp.client import ClientSession

async def main():
    print("hello")

    counts = range(1, 50)

    async with aiohttp.ClientSession() as session:

        co_list = list(map(lambda x: call_async(session, x), counts))

        await asyncio.gather(*co_list)
        
    print("end")

async def call_async(session: ClientSession, count: int):
    print(f"start {count}.")
    wait = 20
    if (count - 1) % 5 == 0:
        wait = 2000
    async with session.get(f"http://localhost:8080/api/with-blocking/{wait}") as response:
        content = await response.text()
        print(f"result {count}: {content}.")

if __name__ == "__main__":
    start = time.time()
    loop = asyncio.get_event_loop()    
    loop.run_until_complete(main())
    end = time.time()
    print('process time = {} [sec]'.format(end - start))
