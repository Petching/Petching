function setCookie(name: string, value: string) {
  document.cookie = `${name}=${value}`;
}

function getCookie(name: string): string {
  const cookieValue = document.cookie
    .split('; ')
    .find(row => row.startsWith(name + '='))
    ?.split('=')[1];
  return cookieValue || '';
}

export { setCookie, getCookie };
