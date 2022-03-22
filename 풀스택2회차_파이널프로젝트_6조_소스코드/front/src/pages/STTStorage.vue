<template>
  <div class="field">
    <div class="top-container px-5 py-5">
      <div class="profile-logo">
        <div style="text-align: left">
          <h2>매체변환 보관함</h2>
        </div>
        <div style="margin-left: 34.5%">
          <h2 @click="$router.push('/')">PAGO BOOKS</h2>
        </div>
      </div>
      <!--profile-logo-end-->
    </div>
    <!--top-container-end-->

    <div class="storage-bottom-container px-5 pt-5 pb-2 justify-content-center">
      <SSM :state="3" />
    </div>
    <!--storage-bottom-container-end-->
  </div>
  <!--field_end-->

  <div
    class="
      row
      storage-bottom-container
      px-5
      pt-2
      pb-2
      row-cols-xl-3
      justify-content-center
    "
    style="clear: both"
  >
    <STTStorageCard
      :document_no="a.document_no"
      :email="a.email"
      :kind="a.kind"
      :input="a.input"
      :output="a.output"
      :trans_date="a.trans_date"
      v-for="(a, i) in array"
      :key="i"
    />
  </div>
</template>

<script>
// import $ from 'jquery'
import axios from "axios";
import STTStorageCard from "@/components/STTStorageCard.vue";
import SSM from "@/components/StorageSelectMenu.vue";

export default {
  name: "papagoPage",
  data() {
    return {
      array: [],
    };
  },
  components: {
    STTStorageCard,
    SSM,
  },
  async mounted() {
    if (
      this.$store.state.userInfo.email != null ||
      this.$store.state.userInfo.email != ""
    ) {
      this.$store.dispatch("setLoading", true);
      await axios
        .get("/api/Stt/list" + "/" + this.$store.state.userInfo.email)
        .then((res) => {
          console.log(res.data);
          this.array = [];
          this.array = res.data;

          for (var i = 0; i < this.array.length; i++) {
            this.array[i].input =
              "http://localhost:8200/resources/document_trans/" +
              this.$store.state.userInfo.email +
              "/" +
              this.array[i].input;
          }
        })
        .catch((err) => {
          console.log(err);
          console.log("다운로드 실패");
        });
      this.$store.dispatch("setLoading", false);
    }
  },
  methods: {},
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
  display: flex;
  background: white;
  border-radius: 100px 0px 0px 0px;
}

.storage-ts-container {
  width: 80%;
  display: flex;
  justify-content: center;
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

select:hover {
  border-color: none;
}

select:focus {
  outline: none;
}

.storage-img-box {
  width: 100%;
  height: 300px;
  border: 1px solid #dbdbdb;
  border-radius: 10px;
  overflow: auto;
}

.storage-img-box::-webkit-scrollbar {
  display: none;
}

.img-fit {
  width: 100%;
  border-radius: 10px;
  object-fit: contain;
  display: block;
}

.storage-ts-output-cont {
  width: 50%;
  margin: 1rem;
  padding: 2rem 3rem 2rem 3rem;
  border: 1px solid #dbdbdb;
  border-radius: 25px;
}

.output-record {
  text-align: end;
}

.output-record > button {
  text-align: center;
  width: 20%;
  padding: 1.25rem;
  background: #0d66ff;
  border-radius: 50px 0px 0px 0px;
}
</style>