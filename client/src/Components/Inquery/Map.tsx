import React, { useEffect } from 'react';

declare global {
  interface Window {
    kakao: any;
  }
}

const MapContainer = () => {
  useEffect(() => {
    const container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
    const options = {
      //지도를 생성할 때 필요한 기본 옵션
      center: new window.kakao.maps.LatLng(
        37.58203340383761,
        126.81311841037957,
      ), //지도의 중심좌표.
      level: 3, //지도의 레벨(확대, 축소 정도)
    };

    // 마커가 표시될 위치입니다
    const markerPosition = new window.kakao.maps.LatLng(
      37.58203340383761,
      126.81311841037957,
    );

    // 마커를 생성합니다
    const marker = new window.kakao.maps.Marker({
      position: markerPosition,
    });

    const map = new window.kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

    marker.setMap(map);
  }, []);

  return <div id="map" style={{ width: '500px', height: '500px' }} />;
};

export default MapContainer;
