Hello Admob
===========

애드몹을 띄우는 법, 전체 광고를 사용하는 법, 종료시 광고 다이얼로그를 띄우는 법이 포함되어 있는 프로젝트입니다. 

AdmobMainActivity 에서 해당 관련 부분을 확인

1. AD_UNIT_ID 에 애드몹 콘솔 - Monetize 탭에서 자신의 앱에서 새 Id (Quit Banner Id 같은 것으로)를 생성해서 그 아이디를 사용할 것

2. 세로 전용 앱일 경우는, 알아서 quitLargeBannerAdView에 관련된 코드를 제거할 것
-> QuitAdDialogFactory에서는 동현 파트를 전부 제거하고 바로 윗 부분 주석을 해제하면 됨
-> 해당 코드는 가로 모드에서 medium 크기의 광고를 출력하지 못하는 사이즈인지 크기를 재서 작을 경우 largeBanner 사이즈 크기의 광고를 송출할 수 있게 해 주는 것
