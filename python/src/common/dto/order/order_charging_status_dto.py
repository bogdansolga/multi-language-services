from typing import Optional

from pydantic import BaseModel


class OrderChargingStatusDTO(BaseModel):
    successful: bool
    failure_reason: Optional[str] = None

    class Config:
        from_attributes = True
