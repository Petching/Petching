import React, { useState } from 'react';
import { Map, MapMarker, Roadview, RoadviewMarker } from 'react-kakao-maps-sdk';

declare global {
  interface Window {
    kakao: any;
  }
}

const MapContainer = () => {
  const [toggle, setToggle] = useState('map');

  const placePosition = {
    lat: 37.58194842014786,
    lng: 126.81493569640868,
  };

  return (
    <div style={{ width: '50vw', height: '50vh', position: 'relative' }}>
      <Map // 로드뷰를 표시할 Container
        center={placePosition}
        style={{
          display: toggle === 'map' ? 'block' : 'none',
          width: '100%',
          height: '100%',
        }}
        level={3}
      >
        <MapMarker position={placePosition} />
        {toggle === 'map' && (
          <input
            style={{
              position: 'absolute',
              top: '5px',
              left: '5px',
              zIndex: 10,
              color: 'black',
              cursor: 'pointer',
              border: 'none',
              borderRadius: 3,
              padding: '5px',
              backgroundColor: '#F5F5EB',
            }}
            type="button"
            onClick={() => setToggle('roadview')}
            title="로드뷰 보기"
            value="로드뷰 보기"
          />
        )}
      </Map>
      <Roadview // 로드뷰를 표시할 Container
        position={{ ...placePosition, radius: 50 }}
        style={{
          display: toggle === 'roadview' ? 'block' : 'none',
          width: '100%',
          height: '100%',
        }}
      >
        <RoadviewMarker position={placePosition} />
        {toggle === 'roadview' && (
          <input
            style={{
              position: 'absolute',
              top: '5px',
              left: '5px',
              zIndex: 10,
              color: 'black',
              cursor: 'pointer',
              border: 'none',
              borderRadius: 3,
              padding: '5px',
              backgroundColor: '#DEDED5',
            }}
            type="button"
            onClick={() => setToggle('map')}
            title="지도 보기"
            value="지도 보기"
          />
        )}
      </Roadview>
    </div>
  );
};

export default MapContainer;
