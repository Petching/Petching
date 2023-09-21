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

function removeCookie() {
  document.cookie =
    'REFRESH_TOKEN' + '=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
}

export { setCookie, getCookie, removeCookie };

// 테스트주석
