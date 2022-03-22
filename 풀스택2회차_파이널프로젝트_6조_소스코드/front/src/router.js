import { createWebHistory, createRouter } from "vue-router";
import { store } from './store.js';

import Home from '/src/pages/Home.vue'
import Papago from '/src/pages/Papago.vue'
import OCR from '/src/pages/OCR.vue'
import STT from '/src/pages/STT.vue'
import TTS from '/src/pages/TTS.vue'
import Storage from '/src/pages/Storage.vue'
import OCRStorage from '/src/pages/OCRStorage.vue'
import PapagoStorage from '/src/pages/PapagoStorage.vue'
import STTStorage from '/src/pages/STTStorage.vue'
import TTSStorage from '/src/pages/TTSStorage.vue'

const routes = [
    {
        path: "/",
        component: Home,
    },
    {
        path: "/papago",
        component: Papago,
    },
    {
        path: "/ocr",
        component: OCR,
    },
    {
        path: "/stt",
        component: STT,
    },
    {
        path: "/tts",
        component: TTS,
    },
    {
        path: "/storage",
        component: Storage,
    },
    {
        path: "/OCRStorage",
        component: OCRStorage,
        beforeEnter: (to, from, next) => {
            // 만약 로그인 상태라면
            if (store.state.userInfo !== null) {
                //alert(store.state.userInfo.email + "님의 보관함 페이지로 이동합니다.");
                next();
            } else {
                alert("로그인이 필요한 서비스입니다.");
                next("/");
            }
        },
    },
    {
        path: "/PapagoStorage",
        component: PapagoStorage,
        beforeEnter: (to, from, next) => {
            // 만약 로그인 상태라면
            if (store.state.userInfo !== null) {
                //alert(store.state.userInfo.email + "님의 보관함 페이지로 이동합니다.");
                next();
            } else {
                alert("로그인이 필요한 서비스입니다.");
                next("/");
            }
        },
    },
    {
        path: "/STTStorage",
        component: STTStorage,
        beforeEnter: (to, from, next) => {
            // 만약 로그인 상태라면
            if (store.state.userInfo !== null) {
                //alert(store.state.userInfo.email + "님의 보관함 페이지로 이동합니다.");
                next();
            } else {
                alert("로그인이 필요한 서비스입니다.");
                next("/");
            }
        },
    },
    {
        path: "/TTSStorage",
        component: TTSStorage,
        beforeEnter: (to, from, next) => {
            // 만약 로그인 상태라면
            if (store.state.userInfo !== null) {
                //alert(store.state.userInfo.email + "님의 보관함 페이지로 이동합니다.");
                next();
            } else {
                alert("로그인이 필요한 서비스입니다.");
                next("/");
            }
        },
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router; 