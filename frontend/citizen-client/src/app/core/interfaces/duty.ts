import DutyCategory from "./duty-category";

interface Duty {
    id: string | null,
    department: string,
    description: string,
    category: DutyCategory | null
}

export default Duty