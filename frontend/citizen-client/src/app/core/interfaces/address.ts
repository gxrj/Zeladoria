import District from './district'

interface Address {
    latitude: number | null,
    longitude: number | null,
    zip_code: string | null,
    public_place: string | null,
    district: District | null,
    reference: string | null
}

export default Address
