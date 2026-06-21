package com.atlas.atlas_backend.config;

import com.atlas.atlas_backend.discussions.entity.Discussion;
import com.atlas.atlas_backend.discussions.repository.DiscussionRepository;
import com.atlas.atlas_backend.drops.entity.Drop;
import com.atlas.atlas_backend.drops.repository.DropRepository;
import com.atlas.atlas_backend.topics.entity.Topic;
import com.atlas.atlas_backend.topics.repository.TopicRepository;
import com.atlas.atlas_backend.users.entity.User;
import com.atlas.atlas_backend.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final DropRepository dropRepository;
    private final DiscussionRepository discussionRepository;

    @Override
    public void run(String... args) {

        if (topicRepository.count() > 0) return;

        // ── Users ────────────────────────────────────────────────────────────
        User aneesh = User.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .username("aneesh").displayName("Aneesh Siddhartha")
                .status("ACTIVE").explorerNumber(1).build();
        User maya   = User.builder().username("maya").displayName("Maya Chen").status("ACTIVE").explorerNumber(2).build();
        User alex   = User.builder().username("alex").displayName("Alex Morgan").status("ACTIVE").explorerNumber(3).build();
        User sara   = User.builder().username("sara").displayName("Sara Kim").status("ACTIVE").explorerNumber(4).build();
        User ravi   = User.builder().username("ravi").displayName("Ravi Shankar").status("ACTIVE").explorerNumber(5).build();
        User priya  = User.builder().username("priya").displayName("Priya Nair").status("ACTIVE").explorerNumber(6).build();
        User tom    = User.builder().username("tom").displayName("Tom Eriksen").status("ACTIVE").explorerNumber(7).build();
        User divya  = User.builder().username("divya").displayName("Divya Reddy").status("ACTIVE").explorerNumber(8).build();
        User arjun  = User.builder().username("arjun").displayName("Arjun Mehta").status("ACTIVE").explorerNumber(9).build();
        User lisa   = User.builder().username("lisa").displayName("Lisa Park").status("ACTIVE").explorerNumber(10).build();

        userRepository.saveAll(List.of(aneesh, maya, alex, sara, ravi, priya, tom, divya, arjun, lisa));

        // ── Topics ───────────────────────────────────────────────────────────
        Topic macbook      = Topic.builder().title("MacBook Air M4").createdBy(aneesh.getId()).build();
        Topic bangalore    = Topic.builder().title("Living in Bangalore").createdBy(maya.getId()).build();
        Topic roads        = Topic.builder().title("Why are roads bad in Bangalore?").createdBy(aneesh.getId()).build();
        Topic chatgpt      = Topic.builder().title("ChatGPT").createdBy(sara.getId()).build();
        Topic movies       = Topic.builder().title("Best movies of all time").createdBy(alex.getId()).build();
        Topic games        = Topic.builder().title("Games I wish I played earlier").createdBy(ravi.getId()).build();
        Topic cafes        = Topic.builder().title("Best cafes in Bengaluru").createdBy(maya.getId()).build();
        Topic windowsToMac = Topic.builder().title("Switching from Windows to Mac").createdBy(alex.getId()).build();
        Topic productivity = Topic.builder().title("Productivity tools that actually work").createdBy(sara.getId()).build();
        Topic remote       = Topic.builder().title("Remote work in India — is it sustainable?").createdBy(ravi.getId()).build();
        Topic coding       = Topic.builder().title("Best coding setup for developers").createdBy(arjun.getId()).build();
        Topic notion       = Topic.builder().title("Notion — worth it or overhyped?").createdBy(priya.getId()).build();
        Topic iPhone       = Topic.builder().title("iPhone 16 Pro — real-world experience").createdBy(tom.getId()).build();
        Topic running      = Topic.builder().title("Starting to run — what nobody tells you").createdBy(divya.getId()).build();
        Topic investing    = Topic.builder().title("Investing in India as a salaried professional").createdBy(lisa.getId()).build();

        topicRepository.saveAll(List.of(
                macbook, bangalore, roads, chatgpt, movies, games,
                cafes, windowsToMac, productivity, remote, coding, notion, iPhone, running, investing
        ));

        // ── Drops ────────────────────────────────────────────────────────────

        // MacBook Air M4 (6 drops)
        Drop mb1 = drop(macbook, aneesh, "Battery life is ridiculous. I charge it once every two days even with heavy dev work.");
        Drop mb2 = drop(macbook, alex, "The MacBook Air M4 is incredible for development but not for gaming. No fan means it throttles under sustained load.");
        Drop mb3 = drop(macbook, sara, "Fanless design means complete silence. I didn't realize how much fan noise was stressing me out until it was gone.");
        Drop mb4 = drop(macbook, ravi, "Switched from a Dell XPS and the MacBook Air M4 feels like a different category of laptop. Display is stunning.");
        Drop mb5 = drop(macbook, priya, "The MagSafe connector is underrated. Knocking the cable out of a sleeping laptop without losing work changed my life.");
        Drop mb6 = drop(macbook, tom, "Memory bandwidth on M4 is genuinely transformative for running local AI models. 16GB feels like 32GB on x86.");

        // Bangalore (6 drops)
        Drop b1 = drop(bangalore, maya, "Bangalore accelerated my career more than any other city. The density of opportunity is unmatched.");
        Drop b2 = drop(bangalore, sara, "Love the weather but traffic drains me every single day. The commute cost in time is enormous.");
        Drop b3 = drop(bangalore, aneesh, "The startup ecosystem here is real. You run into founders at coffee shops — a different energy than any other Indian city.");
        Drop b4 = drop(bangalore, ravi, "Koramangala to Whitefield during peak hours is legitimately the worst commute I've ever experienced.");
        Drop b5 = drop(bangalore, priya, "Finding affordable housing within 5km of work took me four months. The rental market is brutal for newcomers.");
        Drop b6 = drop(bangalore, divya, "The weather in Bangalore is genuinely the best in India. Never too hot, never too cold. That alone is worth the other trade-offs.");

        // Roads (5 drops)
        Drop r1 = drop(roads, aneesh, "Infrastructure simply hasn't kept pace with population growth. The city doubled in a decade, the roads didn't.");
        Drop r2 = drop(roads, maya, "Metro construction has made things significantly worse in the short term. Hopefully the payoff is worth it.");
        Drop r3 = drop(roads, ravi, "Utility companies dig up the same road three times a year and nobody coordinates. That's the root cause.");
        Drop r4 = drop(roads, arjun, "The monsoon season reveals exactly how bad the drainage design is. Same spots flood every single year.");
        Drop r5 = drop(roads, lisa, "Moving to Indiranagar fixed my road problem. The inner areas are far better maintained than the main corridors.");

        // ChatGPT (6 drops)
        Drop cg1 = drop(chatgpt, sara, "ChatGPT became my default starting point for almost every learning task. Changed how I approach problems.");
        Drop cg2 = drop(chatgpt, alex, "The gap between GPT-4 and GPT-3.5 was the moment I realized this was a genuine step change in capability.");
        Drop cg3 = drop(chatgpt, aneesh, "I use it for first drafts of everything — emails, docs, code scaffolding. The blank page problem is gone.");
        Drop cg4 = drop(chatgpt, maya, "The confident wrongness is the biggest risk. It sounds authoritative even when it's completely fabricated.");
        Drop cg5 = drop(chatgpt, tom, "For debugging, it's remarkable. You paste an error and it usually explains exactly what went wrong.");
        Drop cg6 = drop(chatgpt, priya, "I asked it to explain quantum entanglement to me like I was 10. Best explanation I've ever read anywhere.");

        // Movies (6 drops)
        Drop mv1 = drop(movies, alex, "Interstellar ruined many movies for me because the bar became impossibly high after watching it.");
        Drop mv2 = drop(movies, maya, "Parasite is the only movie I rewatched immediately after the first viewing.");
        Drop mv3 = drop(movies, ravi, "3 Idiots shaped an entire generation's view on education in India. Insane cultural impact for a comedy.");
        Drop mv4 = drop(movies, divya, "The Dark Knight still holds up. The Joker is the greatest villain performance ever put on film.");
        Drop mv5 = drop(movies, lisa, "Dune Part Two is the most visually stunning film I've seen since Blade Runner 2049.");
        Drop mv6 = drop(movies, arjun, "Whiplash is 107 minutes and not a single second is wasted. Technically a music film, really a film about obsession.");

        // Games (6 drops)
        Drop g1 = drop(games, ravi, "Red Dead Redemption 2 is the game I wish I'd played ten years ago. Nothing has matched the feeling of that world.");
        Drop g2 = drop(games, alex, "The original Portal is two hours long and contains more creative design than most franchises manage in a decade.");
        Drop g3 = drop(games, tom, "Hollow Knight is a masterpiece that I almost quit in the first hour. The learning curve is brutal but worth it.");
        Drop g4 = drop(games, aneesh, "Disco Elysium is unlike anything else. It's the only game that reads like a novel and plays like a dream.");
        Drop g5 = drop(games, divya, "Celeste treats difficulty as an accessibility setting and as an emotional metaphor simultaneously. Brilliant design.");
        Drop g6 = drop(games, lisa, "Stardew Valley seemed too simple when I first saw it. 200 hours later I understand what everyone was talking about.");

        // Cafes (6 drops)
        Drop ca1 = drop(cafes, maya, "Matteo Coffee in Indiranagar has the best pour-over in the city. Worth the price and the wait.");
        Drop ca2 = drop(cafes, sara, "Dyu Art Cafe in Koramangala is the only place I can reliably get deep work done. The vibe is exactly right.");
        Drop ca3 = drop(cafes, aneesh, "Third Wave Coffee is consistent and everywhere — the reliable choice even if not the most interesting.");
        Drop ca4 = drop(cafes, priya, "Curious Life Coffee in HSR is underrated. Excellent beans, no queues, free parking.");
        Drop ca5 = drop(cafes, arjun, "Corridor Seven in Koramangala roasts their own beans. Serious coffee for serious people.");
        Drop ca6 = drop(cafes, divya, "The Biere Club has surprising coffee even though it's primarily a bar. Their outdoor seating on a cool Bangalore morning is unbeatable.");

        // Windows to Mac (5 drops)
        Drop wm1 = drop(windowsToMac, alex, "The first week switching from Windows to Mac is genuinely disorienting. The second week you wonder how you ever used Windows.");
        Drop wm2 = drop(windowsToMac, ravi, "Missing right-click context menu was the biggest adjustment. Mac gestures are more powerful but have a real learning curve.");
        Drop wm3 = drop(windowsToMac, tom, "Mission Control replaced my Alt+Tab workflow and is now better than anything I used on Windows.");
        Drop wm4 = drop(windowsToMac, priya, "The software library gap is real for specific tools. Adobe suite, Figma, VS Code — all fine. Anything niche — you might struggle.");
        Drop wm5 = drop(windowsToMac, sara, "Keyboard shortcuts are completely different and muscle memory takes about a month to override. Plan for productivity loss.");

        // Productivity (6 drops)
        Drop pr1 = drop(productivity, sara, "Notion replaced six different apps for me. It's not perfect but the consolidation alone saved hours a week.");
        Drop pr2 = drop(productivity, aneesh, "Time blocking actually works but only if you treat the blocks as important as external meetings. That mental shift is the hard part.");
        Drop pr3 = drop(productivity, maya, "Obsidian for note-taking changed how I think. Having a second brain that connects ideas changed what I'm capable of.");
        Drop pr4 = drop(productivity, arjun, "The two-minute rule from GTD is the most actionable productivity insight I've found. If it takes under 2 minutes, do it now.");
        Drop pr5 = drop(productivity, lisa, "A physical notebook for daily todos outperforms any app I've tried. The act of writing something down creates commitment.");
        Drop pr6 = drop(productivity, tom, "Linear for project tracking at work, Notion for personal knowledge. Don't try to make one tool do everything.");

        // Remote (5 drops)
        Drop rw1 = drop(remote, ravi, "Remote work in India is sustainable if you're senior enough to own your output. Junior roles need the osmosis of being in a room.");
        Drop rw2 = drop(remote, maya, "Power cuts and internet reliability in non-metro cities make remote work genuinely difficult outside Bangalore and Mumbai.");
        Drop rw3 = drop(remote, divya, "I moved back to my hometown in Kerala to work remotely. Quality of life 10x better. Career growth — I'm watching carefully.");
        Drop rw4 = drop(remote, lisa, "The async culture of remote work requires explicitness about everything. Over-communication feels weird at first but becomes essential.");
        Drop rw5 = drop(remote, tom, "Home office setup investment is not optional for remote work. A proper desk, monitor, and chair took my productivity from 60% to 100%.");

        // Coding (6 drops)
        Drop cd1 = drop(coding, arjun, "A 27-inch external monitor paired with a laptop is the highest ROI setup change I've ever made.");
        Drop cd2 = drop(coding, tom, "Mechanical keyboards are not just for aesthetics. Tactile feedback genuinely reduces typing fatigue over long sessions.");
        Drop cd3 = drop(coding, aneesh, "Neovim took three weeks to learn and now I code faster than I ever did in VS Code. The investment was worth it.");
        Drop cd4 = drop(coding, alex, "VS Code with the right extensions is hard to beat for web development. The extension ecosystem is unparalleled.");
        Drop cd5 = drop(coding, priya, "Proper lighting matters more than people think. Eye strain from bad lighting killed my productivity for a year before I fixed it.");
        Drop cd6 = drop(coding, lisa, "Standing desk changed my back health but the real win was using it to alternate positions, not standing all day.");

        // Notion (5 drops)
        Drop nt1 = drop(notion, priya, "Notion's database views are where the real power is. Most people never discover them and wonder why they don't get it.");
        Drop nt2 = drop(notion, sara, "The performance on mobile is genuinely bad. For a tool that's supposed to be everywhere, the app is surprisingly sluggish.");
        Drop nt3 = drop(notion, alex, "I've tried Notion three times. It clicked on the third try when I stopped trying to replicate what I had elsewhere.");
        Drop nt4 = drop(notion, maya, "Notion AI is actually useful for meeting notes. Not for everything, but for summarizing discussions it saves real time.");
        Drop nt5 = drop(notion, divya, "Obsidian beats Notion for personal knowledge. Notion beats Obsidian for team wikis. Choose based on use case.");

        // iPhone 16 Pro (5 drops)
        Drop ip1 = drop(iPhone, tom, "Camera Control button is one of those hardware decisions that feels gimmicky until you've used it for a week.");
        Drop ip2 = drop(iPhone, maya, "The titanium frame is the biggest physical upgrade in years. Feels completely different to hold.");
        Drop ip3 = drop(iPhone, aneesh, "Photographic Styles in iPhone 16 Pro are the first camera feature in years that genuinely changed how I shoot.");
        Drop ip4 = drop(iPhone, lisa, "Battery life is finally not a complaint. I end the day with 30-40% remaining which has never happened before.");
        Drop ip5 = drop(iPhone, sara, "The price increase was the biggest story. The hardware improvements are real but incremental.");

        // Running (5 drops)
        Drop ru1 = drop(running, divya, "The first month of running felt impossible. Month three, I was running 5km without stopping. It's entirely about consistency.");
        Drop ru2 = drop(running, priya, "Most beginners go too fast and burn out. Zone 2 pace — where you can hold a conversation — is where the real gains happen.");
        Drop ru3 = drop(running, arjun, "Shoes matter enormously. I ran through knee pain for two months before someone told me my shoes were wrong for my gait.");
        Drop ru4 = drop(running, lisa, "Running early morning in Bangalore is transcendent. The city is quiet, the air is cool, and you get the streets to yourself.");
        Drop ru5 = drop(running, maya, "A GPS watch changed my relationship with running. Seeing data — pace, heart rate, cadence — turned it from suffering into a sport.");

        // Investing (5 drops)
        Drop in1 = drop(investing, lisa, "Index funds beat most active funds over 10+ years. The evidence is overwhelming. Start there, not with stock picking.");
        Drop in2 = drop(investing, aneesh, "PPF is criminally underused by young salaried professionals. 7.1% tax-free return with EEE status is hard to beat.");
        Drop in3 = drop(investing, ravi, "SIPs remove the timing problem entirely. Set it up, forget it, check once a year. Simple beats sophisticated here.");
        Drop in4 = drop(investing, alex, "Real estate in Bangalore as an investment is not what it was. The rental yields are 2-3% while EMIs are 8%+. The math doesn't work.");
        Drop in5 = drop(investing, priya, "NPS for the additional 50k tax deduction under 80CCD(1B) is free money. Most people don't even know it exists.");

        dropRepository.saveAll(List.of(
                mb1, mb2, mb3, mb4, mb5, mb6,
                b1, b2, b3, b4, b5, b6,
                r1, r2, r3, r4, r5,
                cg1, cg2, cg3, cg4, cg5, cg6,
                mv1, mv2, mv3, mv4, mv5, mv6,
                g1, g2, g3, g4, g5, g6,
                ca1, ca2, ca3, ca4, ca5, ca6,
                wm1, wm2, wm3, wm4, wm5,
                pr1, pr2, pr3, pr4, pr5, pr6,
                rw1, rw2, rw3, rw4, rw5,
                cd1, cd2, cd3, cd4, cd5, cd6,
                nt1, nt2, nt3, nt4, nt5,
                ip1, ip2, ip3, ip4, ip5,
                ru1, ru2, ru3, ru4, ru5,
                in1, in2, in3, in4, in5
        ));

        // ── Discussions ──────────────────────────────────────────────────────

        // MacBook discussions
        Discussion d_mb1a = disc(mb1, maya, "Would you buy it again today if you had to choose?");
        Discussion d_mb1b = disc(mb1, ravi, "What's your workflow? Heavy compilation or mostly web dev?");
        Discussion d_mb1c = disc(mb1, alex, "I get about the same. Docker containers tank it though.");
        Discussion d_mb2a = disc(mb2, aneesh, "Agreed. I wouldn't recommend it for gaming. But nothing beats it for build speeds.");
        Discussion d_mb2b = disc(mb2, sara, "The gaming situation on Mac is slowly improving with Metal but it's still years behind.");
        Discussion d_mb3a = disc(mb3, ravi, "This was the first thing I noticed too. Complete silence is underrated for focus.");
        Discussion d_mb4a = disc(mb4, maya, "The display jump from a mid-range PC laptop is significant. Liquid Retina really does make a difference.");
        Discussion d_mb5a = disc(mb5, aneesh, "MagSafe saved my laptop at least twice. Worth the trade-off of needing the adapter.");
        Discussion d_mb6a = disc(mb6, arjun, "Running Ollama locally on M4 is genuinely impressive. 13B parameter models run smooth.");
        Discussion d_mb6b = disc(mb6, priya, "Which models are you running? I've been curious about this for code completion.");

        // Bangalore discussions
        Discussion d_b1a = disc(b1, alex, "The opportunities here are hard to beat anywhere else in India.");
        Discussion d_b1b = disc(b1, ravi, "Agreed, though Pune and Hyderabad are catching up faster than people realize.");
        Discussion d_b2a = disc(b2, aneesh, "The weather is the only reason I still tolerate the traffic.");
        Discussion d_b2b = disc(b2, maya, "I switched to a place 2km from office. Quality of life improved more than any salary hike.");
        Discussion d_b3a = disc(b3, sara, "The cafe culture supports this too. Serendipitous product conversations happen everywhere.");
        Discussion d_b4a = disc(b4, divya, "I stopped that commute and went fully remote. Gained back 3 hours a day.");
        Discussion d_b5a = disc(b5, arjun, "Looked at rentals near my office for three months. What I could afford was too far, what was close was too expensive.");
        Discussion d_b6a = disc(b6, tom, "The October-February window here is unbeatable. Perfect sleeping weather.");

        // Roads discussions
        Discussion d_r1a = disc(r1, maya, "Metro construction has made some areas significantly worse in the short term.");
        Discussion d_r1b = disc(r1, ravi, "Population went from 5M to 13M in 20 years. Infrastructure was never designed for this.");
        Discussion d_r2a = disc(r2, aneesh, "The Purple Line opening made Whitefield actually liveable. There's real hope.");
        Discussion d_r3a = disc(r3, sara, "I've seen the same stretch of road in HSR dug up four times in eight months. Four agencies, zero coordination.");
        Discussion d_r4a = disc(r4, maya, "HSR Layout always floods even in moderate rain. The drainage was clearly an afterthought.");
        Discussion d_r5a = disc(r5, ravi, "Indiranagar is genuinely better maintained. The BDA roads there are a different class.");

        // ChatGPT discussions
        Discussion d_cg1a = disc(cg1, alex, "Same. Though I've had to learn to verify its outputs. Confident wrongness is its biggest failure mode.");
        Discussion d_cg1b = disc(cg1, ravi, "It's changed how I write documentation. First draft in 30 seconds, then I edit for accuracy.");
        Discussion d_cg2a = disc(cg2, sara, "GPT-4 on complex reasoning tasks felt like talking to a different kind of tool entirely.");
        Discussion d_cg3a = disc(cg3, maya, "The blank page problem going away is the most underrated thing about it. Momentum matters.");
        Discussion d_cg4a = disc(cg4, tom, "The hallucination problem is real but it's gotten significantly better since GPT-4o. Still needs verification.");
        Discussion d_cg5a = disc(cg5, priya, "I paste stack traces directly into it. Usually explains the root cause better than StackOverflow.");
        Discussion d_cg6a = disc(cg6, divya, "The Feynman technique explanations it gives are often better than textbooks.");

        // Movies discussions
        Discussion d_mv1a = disc(mv1, maya, "Dune Part Two did it for me. Villeneuve is operating at the same altitude as Nolan now.");
        Discussion d_mv2a = disc(mv2, alex, "Parasite is the perfect film to show someone who thinks foreign language films are hard to enjoy.");
        Discussion d_mv3a = disc(mv3, aneesh, "That final scene with Virus is still one of the most emotionally complex moments in any Bollywood film.");
        Discussion d_mv4a = disc(mv4, ravi, "Ledger's Joker is a performance that shouldn't have been possible. He disappeared into that role.");
        Discussion d_mv5a = disc(mv5, arjun, "The sandworm scene in Dune 2 is a new benchmark for large-scale filmmaking.");
        Discussion d_mv6a = disc(mv6, lisa, "Whiplash has the best ending of any film I can think of. The drummer's face says everything.");

        // Games discussions
        Discussion d_g1a = disc(g1, alex, "The ending of RDR2 broke me. I wasn't prepared for what the story does to you.");
        Discussion d_g1b = disc(g1, sara, "I started it three times before it finally grabbed me. Worth persisting through the slow start.");
        Discussion d_g2a = disc(g2, ravi, "Portal 2 is even better. The co-op campaign is some of the best game design ever made.");
        Discussion d_g3a = disc(g3, divya, "I quit Hollow Knight twice. Third attempt I finally beat the first boss and couldn't stop.");
        Discussion d_g4a = disc(g4, maya, "Disco Elysium is the only game I've recommended to people who don't play games.");
        Discussion d_g5a = disc(g5, arjun, "Celeste treating difficulty as an accessibility feature was genuinely revolutionary for the industry.");
        Discussion d_g6a = disc(g6, tom, "Stardew Valley is what I play to decompress after stressful workdays. Perfectly calibrated calmness.");

        // Cafes discussions
        Discussion d_ca1a = disc(ca1, aneesh, "Their single origin Ethiopian is exceptional. Worth going out of your way for.");
        Discussion d_ca2a = disc(ca2, ravi, "Dyu is my go-to for client calls too. Background noise level is perfect — present but not distracting.");
        Discussion d_ca3a = disc(ca3, maya, "Consistency is underrated. When you need to work you don't want to gamble on a new place.");
        Discussion d_ca4a = disc(ca4, sara, "Curious Life has the best wifi of any Bangalore cafe. I've never had a dropped call there.");
        Discussion d_ca5a = disc(ca5, divya, "Their espresso is the real deal. Not the watered-down stuff most places serve.");
        Discussion d_ca6a = disc(ca6, arjun, "Biere Club's outdoor area in November is genuinely one of the best places to sit in all of Bangalore.");

        // Windows to Mac discussions
        Discussion d_wm1a = disc(wm1, aneesh, "Week one I was Googling how to go back. Week three I was recommending it to everyone.");
        Discussion d_wm2a = disc(wm2, sara, "Three-finger swipe for Mission Control took a week to internalize but now I use it constantly.");
        Discussion d_wm3a = disc(wm3, alex, "Spotlight is miles better than Windows Search. One keyboard shortcut for everything is a game changer.");
        Discussion d_wm4a = disc(wm4, ravi, "The one app that killed me switching was my work's VPN tool. Took two weeks to get a Mac version.");
        Discussion d_wm5a = disc(wm5, priya, "The Cmd vs Ctrl muscle memory problem is real. Give it a month. It rewires.");

        // Productivity discussions
        Discussion d_pr1a = disc(pr1, alex, "I tried Notion three times before it clicked. The database views are where the real power is.");
        Discussion d_pr2a = disc(pr2, ravi, "Time blocking failed for me until I started scheduling buffer time between blocks. Rigidity was the problem.");
        Discussion d_pr3a = disc(pr3, aneesh, "The way Obsidian links notes creates an actual knowledge graph. It changes how you structure thinking.");
        Discussion d_pr4a = disc(pr4, divya, "The two-minute rule is the most consistently useful productivity advice I've encountered.");
        Discussion d_pr5a = disc(pr5, sara, "Physical notebooks for todos sounds old-fashioned but the commitment created by writing is real.");
        Discussion d_pr6a = disc(pr6, arjun, "Linear's keyboard shortcuts make project management actually fast. Most tools are built for mouse users.");

        // Remote discussions
        Discussion d_rw1a = disc(rw1, maya, "The osmosis point is underappreciated. So much learning happens through overhearing conversations.");
        Discussion d_rw2a = disc(rw2, aneesh, "Starlink has changed this for tier-2 cities. The connectivity gap is closing fast.");
        Discussion d_rw3a = disc(rw3, priya, "How's career growth going? That's my biggest hesitation about moving back to my hometown.");
        Discussion d_rw4a = disc(rw4, tom, "Over-communication in async feels weird for the first month then becomes the only way you want to work.");
        Discussion d_rw5a = disc(rw5, lisa, "Standing desk is the best investment I've made for remote work. My back pain disappeared in three months.");

        // Coding discussions
        Discussion d_cd1a = disc(cd1, tom, "Two monitors went from feeling like a luxury to a necessity inside a week.");
        Discussion d_cd2a = disc(cd2, aneesh, "Keychron K2 with brown switches changed how I feel about typing. Not going back to membrane.");
        Discussion d_cd3a = disc(cd3, priya, "I've been wanting to try Neovim but the investment feels high. Worth it for someone doing mostly Python?");
        Discussion d_cd4a = disc(cd4, ravi, "VS Code extensions are why it won. The ecosystem makes everything else feel incomplete.");
        Discussion d_cd5a = disc(cd5, divya, "Bias lighting behind the monitor eliminated most of my eye strain. Simple and cheap fix.");
        Discussion d_cd6a = disc(cd6, maya, "I stand in the morning, sit in the afternoon. The alternation is the key, not the standing itself.");

        // Notion discussions
        Discussion d_nt1a = disc(nt1, sara, "Linked databases across pages is the feature that unlocked everything for me.");
        Discussion d_nt2a = disc(nt2, divya, "The mobile performance issue is a known complaint for years. They've improved it but it's still not great.");
        Discussion d_nt3a = disc(nt3, arjun, "What finally made it click for you? I'm on attempt two and feeling lost.");
        Discussion d_nt4a = disc(nt4, lisa, "Meeting notes templates saved me at least 30 minutes a week.");
        Discussion d_nt5a = disc(nt5, aneesh, "Obsidian for personal thinking, Notion for shared team knowledge. That split makes both tools better.");

        // iPhone discussions
        Discussion d_ip1a = disc(ip1, maya, "Camera Control felt pointless in YouTube reviews. Three days of using it and I understand.");
        Discussion d_ip2a = disc(ip2, aneesh, "The weight reduction is genuinely noticeable after a year with the heavy Pro Max.");
        Discussion d_ip3a = disc(ip3, divya, "The Cinematic mode video has gotten shockingly good. I used it for a short film project.");
        Discussion d_ip4a = disc(ip4, ravi, "Finally. Battery life was the one consistent complaint for years. They actually fixed it.");
        Discussion d_ip5a = disc(ip5, alex, "The price is the only conversation for people on the fence. The hardware is excellent but the jump is hard to justify.");

        // Running discussions
        Discussion d_ru1a = disc(ru1, priya, "The first month is where everyone quits. The breakthrough at month two is worth pushing through.");
        Discussion d_ru2a = disc(ru2, tom, "Zone 2 training changed everything for me. Slowing down made me faster within two months.");
        Discussion d_ru3a = disc(ru3, aneesh, "Gait analysis at a running store costs nothing and can save months of injury. Always start there.");
        Discussion d_ru4a = disc(ru4, lisa, "Cubbon Park at 6am is one of the great urban running experiences. The energy is different there.");
        Discussion d_ru5a = disc(ru5, arjun, "Garmin Forerunner 165 is the entry point for serious running data without the premium price.");

        // Investing discussions
        Discussion d_in1a = disc(in1, ravi, "Nifty 50 index fund with consistent SIP for 15 years. Hard to argue against the data.");
        Discussion d_in2a = disc(in2, maya, "The EEE status of PPF is remarkable. Very few instruments offer that.");
        Discussion d_in3a = disc(in3, aneesh, "My SIP has been running for 6 years. I barely think about it. That's the point.");
        Discussion d_in4a = disc(in4, priya, "Real estate here is driven by speculation, not yield. The rental math is genuinely bad.");
        Discussion d_in5a = disc(in5, divya, "The NPS 80CCD(1B) deduction is literally free money. Takes 30 minutes to set up once.");

        discussionRepository.saveAll(List.of(
                d_mb1a, d_mb1b, d_mb1c, d_mb2a, d_mb2b, d_mb3a, d_mb4a, d_mb5a, d_mb6a, d_mb6b,
                d_b1a, d_b1b, d_b2a, d_b2b, d_b3a, d_b4a, d_b5a, d_b6a,
                d_r1a, d_r1b, d_r2a, d_r3a, d_r4a, d_r5a,
                d_cg1a, d_cg1b, d_cg2a, d_cg3a, d_cg4a, d_cg5a, d_cg6a,
                d_mv1a, d_mv2a, d_mv3a, d_mv4a, d_mv5a, d_mv6a,
                d_g1a, d_g1b, d_g2a, d_g3a, d_g4a, d_g5a, d_g6a,
                d_ca1a, d_ca2a, d_ca3a, d_ca4a, d_ca5a, d_ca6a,
                d_wm1a, d_wm2a, d_wm3a, d_wm4a, d_wm5a,
                d_pr1a, d_pr2a, d_pr3a, d_pr4a, d_pr5a, d_pr6a,
                d_rw1a, d_rw2a, d_rw3a, d_rw4a, d_rw5a,
                d_cd1a, d_cd2a, d_cd3a, d_cd4a, d_cd5a, d_cd6a,
                d_nt1a, d_nt2a, d_nt3a, d_nt4a, d_nt5a,
                d_ip1a, d_ip2a, d_ip3a, d_ip4a, d_ip5a,
                d_ru1a, d_ru2a, d_ru3a, d_ru4a, d_ru5a,
                d_in1a, d_in2a, d_in3a, d_in4a, d_in5a
        ));

        // ── Replies (one level deep) ──────────────────────────────────────────

        discussionRepository.saveAll(List.of(
                // MacBook replies
                reply(d_mb1a, aneesh, "Absolutely. It's the best laptop I've ever owned. The battery alone justifies it.", mb1.getId()),
                reply(d_mb1b, aneesh, "Heavy Kotlin compilation plus Docker. The M4 handles it without breaking a sweat.", mb1.getId()),
                reply(d_mb6b, tom, "I run Llama 3 8B and Mistral 7B. The 16GB model is the minimum — get 24GB if you can.", mb6.getId()),

                // Bangalore replies
                reply(d_b1b, maya, "Hyderabad is serious competition now. HITEC City has world-class infrastructure.", b1.getId()),
                reply(d_b2b, sara, "Living near work is the highest leverage quality-of-life change you can make. Underrated advice.", b2.getId()),
                reply(d_b5a, priya, "Exactly my experience. I ended up in a flatshare for six months before I found something reasonable.", b5.getId()),

                // ChatGPT replies
                reply(d_cg1a, sara, "The verification habit is the key skill. Trust but verify is the only way to use it responsibly.", cg1.getId()),
                reply(d_cg4a, alex, "It's getting better but GPT-4o still confidently makes up citations sometimes.", cg4.getId()),

                // Movies replies
                reply(d_mv1a, alex, "Villeneuve is the most consistent director working today. Every film is a step up.", mv1.getId()),
                reply(d_mv4a, maya, "The Batman with Pattinson gave Ledger's Joker serious competition. Different but equally committed.", mv4.getId()),

                // Games replies
                reply(d_g1a, ravi, "Chapter 6 is when the game truly reveals what it is. Most people give up before that.", g1.getId()),
                reply(d_g1b, alex, "The world building in the first few chapters is the game doing the work to earn your investment.", g1.getId()),
                reply(d_g3a, tom, "Same. The third attempt is always the one that works. It's designed to teach you through failure.", g3.getId()),

                // Cafes replies
                reply(d_ca1a, maya, "The Colombian they had last quarter was the best cup I've had in India.", ca1.getId()),
                reply(d_ca4a, priya, "The wifi thing alone is worth more than the coffee if you're a remote worker.", ca4.getId()),

                // Productivity replies
                reply(d_pr3a, maya, "The graph view in Obsidian is initially overwhelming but once you see clusters forming it's fascinating.", pr3.getId()),
                reply(d_pr6a, arjun, "Linear cycles make sprint planning actually fast. The keyboard-first design philosophy shows everywhere.", pr6.getId()),

                // Remote replies
                reply(d_rw3a, divya, "Six months in. One level change, one promotion conversation. Watching carefully but cautiously optimistic.", rw3.getId()),
                reply(d_rw5a, ravi, "FlexiSpot is the best value standing desk. The Uplift is better but twice the price.", rw5.getId()),

                // Coding replies
                reply(d_cd3a, arjun, "For Python absolutely worth it. I made the switch for Python work and it transformed my workflow.", cd3.getId()),
                reply(d_cd5a, divya, "Govee bias light with the TV strip. Under 2000 rupees and completely transformed my setup.", cd5.getId()),

                // Investing replies
                reply(d_in1a, lisa, "UTI Nifty 50 or Mirae Asset Large Cap are the go-to choices. Low expense ratio is everything.", in1.getId()),
                reply(d_in3a, ravi, "The key is never stopping during a crash. That's when people ruin a decade of compounding.", in3.getId()),

                // Notion replies
                reply(d_nt3a, priya, "Start with one database for one use case. I started with a reading list. Everything else followed.", nt3.getId()),

                // Running replies
                reply(d_ru2a, divya, "Maffetone method is the framework for this. It felt embarrassing to run that slowly at first.", ru2.getId()),
                reply(d_ru3a, ravi, "Brooks Running store does free gait analysis. Best 30 minutes I've spent related to running.", ru3.getId()),

                // iPhone replies
                reply(d_ip4a, tom, "The A18 Pro chip also runs games at desktop-class quality now. The battery improvements came from efficiency gains.", ip4.getId())
        ));

        System.out.println("Atlas seed data loaded: 10 users, 15 topics, 85 drops, 80 discussions, 28 replies.");
    }

    private Drop drop(Topic topic, User author, String content) {
        return Drop.builder()
                .topicId(topic.getId())
                .authorId(author.getId())
                .content(content)
                .build();
    }

    private Discussion disc(Drop drop, User author, String content) {
        return Discussion.builder()
                .dropId(drop.getId())
                .authorId(author.getId())
                .content(content)
                .build();
    }

    private Discussion reply(Discussion parent, User author, String content, UUID dropId) {
        return Discussion.builder()
                .dropId(dropId)
                .authorId(author.getId())
                .content(content)
                .parentDiscussionId(parent.getId())
                .build();
    }
}
