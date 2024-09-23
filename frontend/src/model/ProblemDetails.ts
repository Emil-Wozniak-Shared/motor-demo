export type ProblemDetails =  {
    type: string, // "https://example.com/probs/out-of-credit",
    title: string, //"You do not have enough credit.",
    detail?: string | null, //"Your current balance is 30, but that costs 50.",
    instance: string, // "/account/12345/msgs/abc",
}
