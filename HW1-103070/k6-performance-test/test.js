import http from "k6/http";

const BASE_URL = __ENV.BASE_URL || "http://localhost:8080";

export const options = {
  stages: [
  { duration: '5s', target: 200 },
  { duration: '10s', target: 200 },
  { duration: '5s', target: 0 },
  ],
};

export default function () {
  let res = http.post(`${BASE_URL}/api/v1/reservations/1`, JSON.stringify(1), {
    headers: {
      "Content-Type": "application/json",
    },
  });
}