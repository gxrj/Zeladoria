interface Address {
    latitude: number | null,
    longitude: number | null,
    zip_code: string | null,
    public_place: string | null,
    district: any,
    reference: string | null
}

export default Address