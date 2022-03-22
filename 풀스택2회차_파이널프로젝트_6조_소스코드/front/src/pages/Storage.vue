<template>
  <div class="field">
    <div class="top-container px-5 py-5">
      <div class="profile-logo">
        <div>
          <h2>보관함</h2>
        </div>
        <div style="margin-left: 43%;">
          <h2 @click="$router.push('/')">PAGO BOOKS</h2>
        </div>
      </div>
      <!--profile-logo-end-->
    </div>
    <!--top-container-end-->

    <div class="storage-bottom-container px-5 pt-5 pb-2 justify-content-center">
      <div>
        <SSM :state="0" />
      </div>
    </div>
    <!--storage-bottom-container-end-->
  </div>
  <div class="storage-info-cont">
    <div class="storage-info mx-5 my-3 px-5">
        <img src="@/assets/step1.png" alt="">
        <h6>로그인을 하시면 파일 저장과 보관함 이용이 가능합니다</h6>
    </div>
    <div class="storage-info mx-5 my-3 px-5">
        <img src="@/assets/step2.png" alt="">
        <h6>파일을 저장을 했다면, 상단 메뉴에서 보관함을 선택하세요</h6>
    </div>
    <div class="storage-info mx-5 my-3 px-5">
        <img src="@/assets/step3.png" alt="">
        <h6>저장한 파일을 확인하고 다운 받아 사용하세요</h6>
    </div>
  </div>
  <!--field_end-->
  <Modal @closeModal="loginModal = false" :loginModal="loginModal" />
</template>

<script>
// import $ from 'jquery'
import Modal from "@/components/Modal.vue";
import SSM from "@/components/StorageSelectMenu.vue";

export default {
  name: "papagoPage",
  data() {
    return {
      array: [],
      loginModal: false,
    };
  },
  components: {
    Modal,
    SSM,
  },
};
</script>

<style>
body {
  margin: 0;
}

textarea {
  resize: none;
}

.profile-logo > div > h2 {
  color: white;
  font-weight: bold;
}

.storage-bottom-container {
  background: white;
  border-radius: 100px 0px 0px 0px;
  overflow: auto;
}

.top-content {
  display: flex;
  justify-content: space-between;
  padding: 2rem 4rem 2rem 4rem;
  color: white;
}

.storage-ts-lg-ch {
  margin-right: 1rem;
  text-align: left;
}

.storage-ts-lg-ch > select {
  font-size: 1rem;
  padding: 0.5rem;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
}

.storage-ff1-btn {
  width: 100%;
  margin-right: 1rem;
  padding: 0.5rem;
  color: #0d66ff;
  background: white;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  font-size: 1rem;
}

.storage-ff2-btn {
  width: 100%;
  margin-left: 1rem;
  padding: 0.5rem;
  color: white;
  background: #0d66ff;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  font-size: 1rem;
}

select:hover {
  border-color: none;
}

select:focus {
  outline: none;
}

.storage-info-cont {
  display: flex;
  margin-bottom: 5.4rem;
}

.storage-info {
  padding: 3rem;
  text-align: center;
  width: 100%;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
}

.storage-info > img{
  width: 100%;
}

.storage-info > h3 {
  color: #767676;
}

.storage-info > h6 {
  margin-top: 3rem;
}

.remove-btn > button {
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  color: white;
  background: red;
  padding: 0px 15px 0px 15px;
}
</style>