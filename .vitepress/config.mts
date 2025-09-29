import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: "/ddocc/",
  head: [['link', { rel: 'icon', href: '/ddocc/hammer.png' }]],
  srcDir: "docs",

  title: "DDOCC",
  description: "TBA",
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    logo: '/hammer.png',
    nav: [
      { text: 'Home', link: '/' },
    ],

    sidebar: {
      '/': [
      ],
    },

    search: {
      'provider': 'local',
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zzhangje/ddocc' }
    ]
  }
})
