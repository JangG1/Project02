<template>
  <div>
    <div class="profileBox mt-5">
      <!--이거 빼고 수정가능-->
      <div @click="loginModal = true">
        <ProfileItem :profile="getProfile" :email="getEmail" />
      </div>
      <!--이거 빼고 수정가능-->
      <div class="logoutBtn" @click="logout" v-show="isLogin">로그아웃</div>
      <div class="logoutBtnEmpty" v-show="!isLogin">로그아웃</div>
    </div>
    <router-view></router-view>
  </div>
  <div class="px-5"><hr /></div>

  <div class="footer container">
    <p class="mx-3">파고북스 이용약관</p>
    <p class="mx-3">의견제안</p>
    <p class="mx-3">개인정보처리방침</p>
    <p class="mx-3">책임의 한계와 법적고지</p>
    <p class="mx-3">준수사항</p>
  </div>
  <div v-show="!isLogin">
    <Modal @closeModal="loginModal = false" :loginModal="loginModal" />
  </div>
  <div class="layerPopup" v-show="isLoading">
    <div class="spinner"></div>
  </div>
</template>

<script>
import ProfileItem from "@/components/ProfileItem.vue";
import Modal from "@/components/Modal.vue";

export default {
  name: "App",
  data() {
    return {
      loginModal: false,
    };
  },
  mounted() {
    console.log(this.isLogin);
  },
  components: {
    ProfileItem,
    Modal,
  },
  computed: {
    getProfile() {
      if (this.$store.state.userInfo == null)
        return require("@/assets/weblogin1.png");
      return this.$store.state.userInfo.profile;
    },
    getEmail() {
      if (this.$store.state.userInfo == null) return null;
      return this.$store.state.userInfo.email;
    },
    isLogin() {
      return this.$store.state.isLogin;
    },
    isLoading() {
      console.log("loading " + this.$store.state.isLoad);
      return this.$store.state.isLoad;
    },
  },
  methods: {
    logout() {
      this.$store.commit("logout");
      this.$router.go("/");
    },
    showModal() {
      return true;
    },
  },
};
</script>

<style>
.profileBox {
  float: right;
  margin-right: 3rem;
}

.profileBox > div {
  float: left;
}

.logoutBtn {
  color: white;
  border: 1px solid white;
  border-radius: 10px;
  cursor: pointer;
  padding: 10px 20px 10px 20px;
}

.logoutBtnEmpty {
  color: #0d66ff;
  border: 1px solid #0d66ff;
  border-radius: 10px;
  cursor: pointer;
  padding: 10px 20px 10px 20px;
}
.layerPopup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.8);
  z-index: 1000;
  justify-content: center;
  align-items: center;
  margin: 0 0 0 0;
}
.spinner {
  position: absolute;
  top: 50%;
  left: 50%;
  border: 8px solid #f3f3f3; /* Light grey */
  border-top: 8px solid #3498db; /* Blue */
  border-radius: 50%;
  width: 60px;
  height: 60px;
  animation: spinner 2s linear infinite;
}
@keyframes spinner {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>
