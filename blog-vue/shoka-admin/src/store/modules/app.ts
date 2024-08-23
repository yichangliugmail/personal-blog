import { defineStore } from "pinia";
import { AppState } from "../interface";
import { createPinia } from 'pinia'


const useAppStore = defineStore("useAppStore", {
  state: (): AppState => ({
    isCollapse: false,
    device: "desktop",
    size: "default",
  }),
  actions: {
    toggle() {
      this.isCollapse = !this.isCollapse;
    },
    changeCollapse(isCollapse: boolean) {
      this.isCollapse = isCollapse;
    },
    toggleDevice(device: string) {
      this.device = device;
    },
    setSize(size: string) {
      this.size = size;
    },
  },
  getters: {},
  persist: {
    storage: localStorage,
  },
});

export default useAppStore;
export const store = createPinia()
