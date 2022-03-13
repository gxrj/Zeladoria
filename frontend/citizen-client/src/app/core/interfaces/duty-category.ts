import Duty from "./duty";

interface DutyCategory {
    id: string | null,
    name: string,
    duties: Duty[] | null
}

export default DutyCategory