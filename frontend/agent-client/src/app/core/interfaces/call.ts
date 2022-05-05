import Address from "./address";
import Citizen from "./citizen";

interface Call {
    status: string,
    duty: any,
    description: string,
    address: Address,
    author: Citizen | any,
    images: Array<string>,
    [key:string]: any
}

export default Call