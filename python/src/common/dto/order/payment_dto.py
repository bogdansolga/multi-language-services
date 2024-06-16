from pydantic import BaseModel


class PaymentDTO(BaseModel):
    name: str
    email: str

    class Config:
        from_attributes = True
