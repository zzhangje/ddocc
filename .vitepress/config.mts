import { defineConfig } from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  base: "/ddocc/",
  head: [['link', { rel: 'icon', href: '/ddocc/hammer.png' }]],
  srcDir: "docs",

  title: "DDOCC",
  description: "TBA",
  locales: {
    'en': {
      label: 'English',
      lang: 'en-US',
      link: '/en/',
    },
    root: {
      label: '简体中文',
      lang: 'zh-CN',
    }
  },
  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    logo: '/hammer.png',

    nav: [
      { text: 'Home', link: '/' },
    ],

    sidebar: {
      '/en/': [
        {
          text: 'Tutorial',
          link: '/',
          items: [
            {
              text: '1. Setup and Quick Start',
              link: '/en/tutorial/1'
            },
            {
              text: '2. Create Your First Subsystem',
              link: '/en/tutorial/2'
            },
            {
              text: '3. Create a Teleop Command',
              link: '/en/tutorial/3'
            },
            {
              text: '4. Create an Auto Command',
              link: '/en/tutorial/4'
            },
            {
              text: '5. Compose an Autonomous Program',
              link: '/en/tutorial/5'
            }
          ]
        },
      ],
      '/': [
        {
          text: 'Java 远征',
          link: '/java/',
          items: [

          ]
        },
        {
          text: '仿真教程',
          link: '/',
          items: [
            {
              text: '1. 软件配置和仿真运行',
              link: '/tutorial/1'
            },
            {
              text: '2. 创建第一个子系统',
              link: '/tutorial/2'
            },
            {
              text: '3. 创建一个遥控命令',
              link: '/tutorial/3'
            },
            {
              text: '4. 创建一个自动命令',
              link: '/tutorial/4'
            },
            {
              text: '5. 编写一个自动程序',
              link: '/tutorial/5'
            }
          ]
        },
      ]
    },

    search: {
      provider: 'local',
    },

    socialLinks: [
      { icon: 'github', link: 'https://github.com/zzhangje/ddocc' }
    ]
  }
})
