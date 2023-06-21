export const authenticate = async (email: string, password: string) => {
  const response = await fetch("/api/authenticate", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ email, password }),
  });

  return response.ok;
};
