interface Token {
    accessToken: string,
    refreshToken: string,
    expiration: number,
    scope: string,
    tokenType: string
}
export default Token