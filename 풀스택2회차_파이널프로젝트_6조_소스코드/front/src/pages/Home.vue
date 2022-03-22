<template>
  <div class="field">
    <div class="top-container px-5 py-5">
      <div class="top-banner my-5">
        <h1 class="display-1" style="font-weight: bold">PAGO BOOKS</h1>
        <h5 class="mt-3">
          당신의 자료를 손쉽게 번역하고, 변환하고, 저장해보세요!
        </h5>
      </div>
    </div>
    <!--top-container-end-->

    <div class="bottom-container px-5 pt-5">
      <div class="main-btn my-5">
        <img
          src="@/assets/1.png"
          alt="문서변환"
          @click="$router.push('/ocr')"
        />
        <img
          src="@/assets/2.png"
          alt="음성변환"
          @click="$router.push('/tts')"
        />
        <img
          src="@/assets/3.png"
          alt="매체번역"
          @click="$router.push('/stt')"
        />
        <img
          src="@/assets/4.png"
          alt="간단번역"
          @click="$router.push('/papago')"
        />
        <img
          src="@/assets/5.png"
          alt="보관함"
          @click="$router.push('/Storage')"
        />
      </div>

      <br /><br />

      <div style="text-align: center; font-weight: bold">
        <p>[ Tip : 로그인을 하시면 자료를 보관하고 내려받을 수 있습니다 ]</p>
      </div>

      <br /><br /><br />
    </div>
    <!--bottom-container-end-->
  </div>
  <!--field-end-->
</template>

<script>
export default {
  name: "HomePage",
  data() {
    return {};
  },
};
</script>

<style>
body {
  margin: 0;
}

.field {
  background: #0d66ff;
}

.top-container {
  width: 100%;
  height: 50%;
}

.profile-logo {
  display: flex;
  cursor: pointer;
}

.title-name {
  justify-content: center;
}

.top-banner > h5 {
  color: #f1f1f5;
  text-align: center;
}

.display-1 {
  color: white;
  text-align: center;
}

.main-btn {
  text-align: center;
}

.main-btn > img {
  cursor: pointer;
}

.bottom-container {
  background: white;
  border-radius: 100px 0px 0px 0px;
}

.footer {
  display: flex;
  justify-content: center;
}

.footer > p {
  margin-top: 0.5rem;
  font-size: 0.75rem;
}
</style>